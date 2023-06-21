package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.repository.IBookRoomRepository;
import com.nhom10.quanlikhachsan.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookRoomService {
    @Autowired
    private IBookRoomRepository bookRoomRepository;
    public List<BookRoom> getAllBookRoom(){
        return bookRoomRepository.findAll();
    }
    public List<BookRoom> getAllBookRoomOfUser(Long id){
        return bookRoomRepository.findAllBookedRoomOfUser(id);
    }
    public BookRoom getBookRoomById(Long id){
        Optional<BookRoom> optional =bookRoomRepository.findById(id);
        return optional.orElse(null);
    }
    public void deleteBookRoom(Long id){
        bookRoomRepository.deleteById(id);
    }
    public void savePayment(User user, Room room, LocalDate checkin, LocalDate checkout, double money, String method){
        BookRoom bookRoom = new BookRoom();
        Date checkin1 = Date.valueOf(checkin);
        Date checkout1 = Date.valueOf(checkout);
        bookRoom.setCheck_in(checkin1);
        bookRoom.setCheck_out(checkout1);
        bookRoom.setMoney(money);
        bookRoom.setUser(user);
        bookRoom.setRoom(room);
        if(method == "off")
            bookRoom.setIsPaid(false);
        else
            bookRoom.setIsPaid(true);
        bookRoomRepository.save(bookRoom);
    }
    public Page<BookRoom> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.bookRoomRepository.findAll(pageable);
    }
    public Page<BookRoom> searchBookRoom(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return bookRoomRepository.searchBookRoom(keyword, pageable);
    }
    public boolean checkEmptyRoom(Room r, LocalDate checkin, LocalDate checkout){
        Date checkin1 = Date.valueOf(checkin);
        Date checkout1 = Date.valueOf(checkout);
        List<BookRoom> bookRooms = bookRoomRepository.findAll();

        if(bookRooms != null){
            for (BookRoom tmp : bookRooms){
                if(tmp.getRoom().getId() == r.getId()){
                    if(!(checkin1.after(tmp.getCheck_out()) || checkout1.before(tmp.getCheck_in()))){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
