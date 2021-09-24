package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderBillingProperties
{
	private String first_name;

	private String last_name;

	private String company_name;

	private String address_1;

	private String address_2;

	private String city;

	private String country;

	private String postcode;

	private String email;

	private String phone;
}
