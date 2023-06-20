package com.nhom10.quanlikhachsan.controller.client;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.services.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookRoomService bookRoomService;
    @Autowired
    private PaypalService paypalService;
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";
    public static long temp;
    public static long tempRoomId;
    @GetMapping("/{id}/{pageNo}")
    public String List_hotel(@PathVariable("id") Long id, @PathVariable("pageNo") int pageNo,
                             Model model, HttpSession session){
        City city = cityService.getCityById(id);
        if(city == null){
            return "client/error/405";
        }
        if(city.getActive() == false){
            return "client/error/405";
        }

        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        if(checkin != null && checkout != null){
            model.addAttribute("checkin", checkin);
            model.addAttribute("checkout", checkout);
        }


        model.addAttribute("city_name", city.getName());
        model.addAttribute("count_hotel", hotelService.countHotelByCityId(id) + 462);
//        model.addAttribute("list_hotel", hotelService.getAllHotelActiveIdCity(id));
        int pageSize = 5;
        Page<Hotel> page = hotelService.findPaginatedByCityId(id, pageNo, pageSize);
        List<Hotel> listHotel = page.getContent();
        model.addAttribute("city_id", city.getId());
        model.addAttribute("list_hotel", listHotel);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "client/hotel/index";
    }
    @PostMapping("/search2")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam("ngayden") LocalDate checkin,
                         @RequestParam("ngaydi") LocalDate checkout, HttpSession session,
                         RedirectAttributes redirectAttributes){

        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);
        City city = cityService.searchCity(keyword);
        if(city == null){
            return "client/error/405";
        }
        redirectAttributes.addAttribute("id", city.getId());
        return "redirect:/hotel/{id}/1";
    }

    @GetMapping("/detail/{id}")
    public String Hotel_detail(@PathVariable("id") Long id, Model model, HttpSession session){
        temp = id;
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }

        Hotel hotel = hotelService.getHotelById(id);
//         Gọi Google Maps Geocoding API để lấy tọa độ từ địa chỉ
//        AIzaSyAsV9GzuyjrHQMS5IEIZL4pjAhbzAIPCzY
//        AIzaSyAmOTTdm9TwR98k9vaolqGuONg-FaUX2lk
//        AIzaSyBzGedy8KmGBxxSyGlUGMyVPhdlREd6BzY
//        AIzaSyB0a4JQrGRBFION_TSc5DWcaQMPTNji4ro
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAmOTTdm9TwR98k9vaolqGuONg-FaUX2lk")
                .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, hotel.getAddress()).await();
            if (results != null && results.length > 0){
                double latitude = results[0].geometry.location.lat;
                double longitude = results[0].geometry.location.lng;

                // Truyền tọa độ lat,lng vào view
                model.addAttribute("latitude", latitude);
                model.addAttribute("longitude", longitude);
            }else{
                // Gán giá trị mặc định khi không có kết quả trả về
                model.addAttribute("latitude", 0.0);
                model.addAttribute("longitude", 0.0);
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu cần thiết
            e.printStackTrace();
            // Gán giá trị mặc định khi có lỗi
            model.addAttribute("latitude", 0.0);
            model.addAttribute("longitude", 0.0);
        }

        model.addAttribute("hotel", hotel);
        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");

        if(checkin != null && checkout != null){
            model.addAttribute("checkin", checkin);
            model.addAttribute("checkout", checkout);
            //create list to store empty rooms
            List<Room> empty_rooms = new ArrayList<>();
            List<Room> rooms = roomService.getAllRoomByHotelId(id);
            //check empty room
            for (Room tmp : rooms){
                if(bookRoomService.checkEmptyRoom(tmp, checkin, checkout)){
                    empty_rooms.add(tmp);
                }
            }
            model.addAttribute("list_room", empty_rooms);
            return "client/hotel/detail";
        }
        model.addAttribute("list_room", roomService.getAllRoomByHotelId(id));
        return "client/hotel/detail";
    }
    @PostMapping("/search-detail")
    public String searchDetail(@RequestParam("ngayden") LocalDate checkin,
                               @RequestParam("ngaydi") LocalDate checkout, HttpSession session,
                               RedirectAttributes redirectAttributes){

        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout",checkout);
        redirectAttributes.addAttribute("id", temp);
        return "redirect:/hotel/detail/{id}";
    }

    @GetMapping("/confirm/{id}")
    public String Confirm_info(@PathVariable("id") Long id, Model model,HttpSession session,
                               RedirectAttributes redirectAttributes){
        tempRoomId = id;
        //get date
        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        if (checkin == null && checkout == null){
            redirectAttributes.addFlashAttribute("message", "Please check availability!");
            redirectAttributes.addAttribute("id", temp);
            return  "redirect:/hotel/detail/{id}";
        }
        //get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        User user = userService.findUserByUserName(username);
        Room room = roomService.getRoomById(id);
        Hotel hotel = hotelService.getHotelByIdRoom(room.getHotel().getId());
        City city = cityService.getCityByIdHotel(hotel.getCity().getId());

        long numDay = ChronoUnit.DAYS.between(checkin, checkout);
        double totalMoney =numDay * room.getRent();

        model.addAttribute("user", user);
        model.addAttribute("city_name", city.getName());
        model.addAttribute("room", room);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("totalMoney", totalMoney);
        return "client/hotel/confirm";
    }
    @GetMapping("/payment")
    public String payment(HttpSession session, RedirectAttributes redirectAttributes){
        //get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUserName(username);
        //get date
        LocalDate checkin = (LocalDate) session.getAttribute("checkin");
        LocalDate checkout = (LocalDate) session.getAttribute("checkout");
        //get room
        Room  room = roomService.getRoomById(tempRoomId);
        long numDay = ChronoUnit.DAYS.between(checkin, checkout);
        //get money
        double totalMoney = numDay * room.getRent();
        //save
        bookRoomService.savePayment(user,room,checkin,checkout,totalMoney,"off");
        return "redirect:/";
    }

    @GetMapping ("/pay")
    public String paymentPaypal(HttpSession session) {
        String test;
        try {
            //get date
            LocalDate checkin = (LocalDate) session.getAttribute("checkin");
            LocalDate checkout = (LocalDate) session.getAttribute("checkout");
            //get room
            Room  room = roomService.getRoomById(tempRoomId);
            long numDay = ChronoUnit.DAYS.between(checkin, checkout);
            //get money
            double totalMoney = numDay * room.getRent();
            double USD  = totalMoney / 23000;;

            Payment payment = paypalService.createPayment(USD, "USD",
                    "paypal","sale","Hotel booking",
                    "http://localhost:8080/hotel/" + CANCEL_URL,
                    "http://localhost:8080/hotel/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "client/paypal/cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                             HttpSession session) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                //get user
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                User user = userService.findUserByUserName(username);
                //get date
                LocalDate checkin = (LocalDate) session.getAttribute("checkin");
                LocalDate checkout = (LocalDate) session.getAttribute("checkout");
                //get room
                Room  room = roomService.getRoomById(tempRoomId);
                long numDay = ChronoUnit.DAYS.between(checkin, checkout);
                //get money
                double totalMoney = numDay * room.getRent();
                //save
                bookRoomService.savePayment(user,room,checkin,checkout,totalMoney,"on");
                return "client/paypal/success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
}
