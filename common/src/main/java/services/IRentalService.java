package services;

import model.domain.Rental;

public interface IRentalService extends IService<Long, Rental> {

    void DeleteClientRentals(Long id);

    void DeleteMovieRentals(Long id);

}
