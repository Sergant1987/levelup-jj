package com.marchenko.medcards.service;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DoctorServiceImp implements DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImp(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getById(Long id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Doctor getByLogin(String login) {
//        Doctor doctor = doctorRepository.find
        return null;
    }

    @Override
    public String getPasswordByLogin(String login) {
        return null;
    }

//    private final CheckerShipsParameter checkerShipsParameter = new CheckerShipsParameter();
//
//    Logger logger = Logger.getLogger(DoctorServiceImp.class.getName());
//
//    @Override
//    public Ship getById(String ids) {
//        checkerShipsParameter.checkId(ids);
//        long id = Long.parseLong(ids);
//        if (!shipRepository.existsById(id)) {
//            throw new NotFoundException();
//        }
//        return shipRepository.findById(id).get();
//    }
//
//    @Override
//    public Page<Ship> getAll(Specification<Ship> shipSpecification, Pageable pageable) {
//        return shipRepository.findAll(shipSpecification, pageable);
//    }
//
//    @Override
//    public Integer count(Specification<Ship> shipSpecification) {
//        return shipRepository.findAll(shipSpecification).size();
//    }
//
//    @Override
//    public Ship create(Ship ship) {
//        checkerShipsParameter.checkName(ship.getName());
//        checkerShipsParameter.checkPlanet(ship.getPlanet());
//        checkerShipsParameter.checkType(ship.getShipType());
//        checkerShipsParameter.checkCrewSize(ship.getCrewSize());
//        checkerShipsParameter.checkSpeed(ship.getSpeed());
//        checkerShipsParameter.checkData(ship.getProdDate());
//        if (ship.getUsed() == null) {
//            ship.setUsed(false);
//        }
//        ship.calculateRating();
//        logger.info(ship.toString());
//        System.out.println(ship);
//        System.out.println(ship.getRating());
//        shipRepository.saveAndFlush(ship);
//        return ship;
//    }
//
//
//    @Override
//    public void delete(String ids) {
//        checkerShipsParameter.checkId(ids);
//        long id = Long.parseLong(ids);
//        if (!shipRepository.existsById(id)) {
//            throw new NotFoundException();
//        }
//        shipRepository.deleteById(id);
//    }
//
//    @Override
//    public Ship update(String ids, Ship ship) {
//        checkerShipsParameter.checkId(ids);
//        long id = Long.parseLong(ids);
//        if (!shipRepository.existsById(id)) {
//            throw new NotFoundException();
//        }
//        Ship updateShip = shipRepository.findById(id).get();
//
//        if (ship.getName() != null) {
//            checkerShipsParameter.checkName(ship.getName());
//            updateShip.setName(ship.getName());
//        }
//        if (ship.getPlanet() != null) {
//            checkerShipsParameter.checkName(ship.getPlanet());
//            updateShip.setPlanet(ship.getPlanet());
//        }
//        if (ship.getShipType() != null) {
//            updateShip.setShipType(ship.getShipType());
//        }
//        if (ship.getProdDate() != null) {
//            checkerShipsParameter.checkData(ship.getProdDate());
//            updateShip.setProdDate(ship.getProdDate());
//        }
//        if (ship.getUsed() != null) {
//            updateShip.setUsed(ship.getUsed());
//        }
//        if (ship.getCrewSize() != null) {
//            checkerShipsParameter.checkCrewSize(ship.getCrewSize());
//            updateShip.setCrewSize(ship.getCrewSize());
//        }
//        if (ship.getSpeed() != null) {
//            checkerShipsParameter.checkSpeed(ship.getSpeed());
//            updateShip.setSpeed(ship.getSpeed());
//        }
//        updateShip.calculateRating();
//        shipRepository.saveAndFlush(updateShip);
//        return updateShip;
//    }


}
