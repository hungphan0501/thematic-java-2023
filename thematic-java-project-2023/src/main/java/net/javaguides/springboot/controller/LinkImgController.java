package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.LinkImg;
import net.javaguides.springboot.repository.LinkImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/linkImg")
public class LinkImgController {

    @Autowired
    LinkImgRepository linkImgRepository;

    @GetMapping
    public ResponseEntity<List<LinkImg>> getAllImgByIdProduct(@RequestParam("idProduct") int idProduct){
       try {
           List<LinkImg> list = linkImgRepository.getAllByIdProduct(idProduct);
           if(list.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<List<LinkImg>>(list, HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/mainColor")
    public ResponseEntity<LinkImg> getMainColor(@RequestParam("idProduct") int idProduct) {
        try {
            LinkImg linkImg = linkImgRepository.getLinkImgByIdProductAndLevel(idProduct,0);
            if(linkImg == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<LinkImg>(linkImg,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
