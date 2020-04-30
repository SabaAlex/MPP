package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieDto extends BaseEntityDto {
    private String title;
    private int yearOfRelease;
    private String mainStar;
    private String director;
    private  String genre;
}
