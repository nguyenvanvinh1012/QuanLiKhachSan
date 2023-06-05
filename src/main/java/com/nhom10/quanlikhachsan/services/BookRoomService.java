package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.repository.IBookRoomRepository;
import com.nhom10.quanlikhachsan.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookRoomService {
    @Autowired
    private IBookRoomRepository bookRoomRepository;
    public List<BookRoom> getAllBookRoom(){
        return bookRoomRepository.findAll();
    }
    public BookRoom getBookRoomById(Long id){
        Optional<BookRoom> optional =bookRoomRepository.findById(id);
        return optional.orElse(null);
    }
    public void deleteBookRoom(Long id){
        bookRoomRepository.deleteById(id);
    }
}
