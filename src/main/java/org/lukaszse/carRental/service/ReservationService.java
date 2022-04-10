package org.lukaszse.carRental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.model.dto.OrderDto;
import org.lukaszse.carRental.repository.OrderRepository;
import org.lukaszse.carRental.repository.OrderSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final OrderRepository orderRepository;
    private final OrderSearchRepository orderSearchRepository;
    private final CarService carService;

    public Reservation getOrder(Integer id) {
        return orderRepository.getById(id);
    }

    public Page<Reservation> getPaginated(final Pageable pageable) {
        return orderSearchRepository.findAll(pageable);
    }

    public Page<Reservation> findOrders(final String orderName, final String contractor, final Pageable pageable) {
        return orderSearchRepository.findReservationByReservationNameContainsAndCar_ModelContainsIgnoreCase(orderName, contractor, pageable);
    }

    public void addEditOrder(OrderDto orderDto) {
        orderRepository.save(createOrderOrGetOrderForUpdate(orderDto));
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    private Reservation createOrderOrGetOrderForUpdate(final OrderDto orderDto) {
        return orderDto.getId() == null ?
                createOrder(orderDto) :
                findOrderAndPrepareForUpdate(orderDto);
    }

    private Reservation createOrder(final OrderDto orderDto) {
        return new Reservation(carService.getContractor(orderDto.getContractorId()),
                new BigDecimal(orderDto.getPrice().replace(",", ".")),
                orderDto.getOrderName(),
                orderDto.getOrderDescription());
    }

    private Reservation findOrderAndPrepareForUpdate(final OrderDto orderDto) {
        Reservation reservation = getOrder(orderDto.getId());
        reservation.setCar(carService.getContractor(orderDto.getContractorId()));
        reservation.setPrice(new BigDecimal(orderDto.getPrice().replace(",", ".")));
        reservation.setReservationName(orderDto.getOrderName());
        reservation.setReservationDescription(orderDto.getOrderDescription());
        return reservation;
    }

}
