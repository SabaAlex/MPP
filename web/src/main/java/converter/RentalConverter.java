package converter;

import core.model.domain.Rental;
import dto.RentalDto;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter extends BaseConverter<Long, Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .ClientID(dto.getClientID())
                .MovieID(dto.getMovieID())
                .year(dto.getYear())
                .month(dto.getMonth())
                .day(dto.getDay())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .ClientID(rental.getClientID())
                .MovieID(rental.getMovieID())
                .year(rental.getYear())
                .month(rental.getMonth())
                .day(rental.getDay())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}
