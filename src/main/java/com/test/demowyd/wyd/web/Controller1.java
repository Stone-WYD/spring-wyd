package com.test.demowyd.wyd.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controller1 {

    private static final Logger log = LoggerFactory.getLogger(Controller1.class);

    @RequestMapping("/myOwnTest")
    public User test5(@RequestParam("test1")String test1,@RequestBody User user ){
        log.info(test1);
        System.out.println(user);
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    public static class User {
        private String name;
        private int age;

        public User(){}

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
