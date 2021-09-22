package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDefaultAttributsProperties
{
	private Integer id;

	private String name;

	private String option;
}
