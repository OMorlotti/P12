package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImagesProperties
{
	private Integer id;

	private Date date_created;

	private Date date_created_gmt;

	private Date date_modified;

	private Date date_modified_gmt;

	private String src;

	private String name;

	private String alt;
}
