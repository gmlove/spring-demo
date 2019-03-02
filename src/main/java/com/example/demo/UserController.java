package com.example.demo;

import com.example.demo.UserController.Dtos.CreatedUserDto;
import com.example.demo.db.Book;
import com.example.demo.db.BookRepository;
import com.example.demo.db.User;
import com.example.demo.db.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public static class Dtos {
        @Data
        @NoArgsConstructor
        public static class CreatedUserDto {
            private Integer id;
            private String name;
            private LocalDateTime updatedAt;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public CreatedUserDto create(@RequestParam(value="name", defaultValue="user name") String name) {
        User user = User.builder().name(name).build();
        user = userRepository.save(user);
        CreatedUserDto createdUserDto = new CreatedUserDto();
        BeanUtils.copyProperties(user, createdUserDto);
        return createdUserDto;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    @ResponseBody
    public User update(@PathVariable(value="userId") Integer userId) {
        userRepository.updateTime(userId);
        return userRepository.findById(userId).get();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable(value="userId") Integer userId) {
        return userRepository.findById(userId).get();
    }

    @RequestMapping(value = "/{userId}/books", method = RequestMethod.POST)
    @ResponseBody
    public Book addBook(@PathVariable(value="userId") Integer userId,
        @RequestParam(value="name", defaultValue="book name") String bookName) {
        Book book = Book.builder().name(bookName).owner(userRepository.findById(userId).get()).build();
        book = bookRepository.save(book);
        return book;
    }

    @RequestMapping(value = "/{userId}/books", method = RequestMethod.GET)
    @ResponseBody
    public Set<Book> getBooks(@PathVariable(value="userId") Integer userId) {
        return userRepository.findById(userId).get().getBooks();
    }


    @RequestMapping(value = "/{userId}/books/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public Book getBook(@PathVariable(value="userId") Integer userId,
        @PathVariable(value="bookId") Integer bookId) {
        Book book = Book.builder().id(bookId).owner(userRepository.findById(userId).get()).build();
        return bookRepository.findOne(Example.of(book)).get();
    }

}