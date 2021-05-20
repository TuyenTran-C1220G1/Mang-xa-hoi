package com.company.controller;

import com.company.model.Image;
import com.company.model.Post;
import com.company.model.PostForm;
import com.company.model.User;
import com.company.service.IImageService;
import com.company.service.impl.PostService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostService postService;
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    IImageService iImageService;



    @PostMapping("/createpost")
    public ModelAndView createPost(Principal principal, @ModelAttribute PostForm postForm){

        User user = userService.findByUsername(principal.getName());
        int likes = 0;
        LocalDateTime time = LocalDateTime.now();
        Set<Image> imageSet = new HashSet<>();
        List<MultipartFile> multipartFiles = postForm.getImages();
        List<String> listFileName = new ArrayList<>();
        for (MultipartFile multipartfiles: multipartFiles
             ) {
            listFileName.add(multipartfiles.getOriginalFilename());
        }
        try {
            for (int i = 0; i < multipartFiles.size(); i++) {
                FileCopyUtils.copy(multipartFiles.get(i).getBytes(), new File(fileUpload + listFileName.get(i)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < listFileName.size(); i++) {
            Image image = new Image();
            image.setImage_link(listFileName.get(i));
            imageSet.add(image);
        }
        for (Image image: imageSet
             ) {
            iImageService.save(image);
        }


        Post post = new Post(postForm.getTitle(),postForm.getContent(),time,user,imageSet, likes,postForm.getStatus());
        postService.save(post);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/testlist")
    public ModelAndView showList(Principal principal){

        User user = userService.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/listtest");
        modelAndView.addObject("listcontent",postService.findAllByUserId(user.getId()));
        return modelAndView;
    }

}
