package core.repository.file;

import core.model.domain.Rental;
import core.model.validators.Validator;

import java.util.List;

public class RentalFileRepository extends FileRepository<Long, Rental> {

    public RentalFileRepository(Validator<Rental> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    protected String formatEntity(Rental entity) {
        return entity.getId() + "," + entity.getClientID() + "," + entity.getMovieID()+","+
                entity.getYear()+","+entity.getMonth()+","+entity.getDay();
    }

    @Override
    protected Rental createEntity(List<String> items) {
        Long id = Long.valueOf(items.get(0));
        Long ClientId =Long.valueOf(items.get(1));
        Long MovieID= Long.valueOf(items.get(2));
        int year= Integer.parseInt(items.get(3));
        int month = Integer.parseInt(items.get(4));
        int day= Integer.parseInt(items.get(5));

        return new Rental(ClientId,MovieID,year,month,day);
    }
}
