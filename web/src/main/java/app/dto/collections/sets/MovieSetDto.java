package app.dto.collections.sets;

import app.dto.ClientDto;
import app.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovieSetDto extends SetDto<MovieDto>{
}
