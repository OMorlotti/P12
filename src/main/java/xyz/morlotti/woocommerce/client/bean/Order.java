package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

import java.lang.reflect.Array;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order
{
	private Integer id;

	private Integer parent_id;

	private String number;

	private String order_key;

	private String version;

	private String status;

	private Date date_created;

	private Date date_modified;

	private String discount_total;

	private String discount_tax;

	private String shipping_total;

	private String shipping_tax;

	private String total;

	private String total_tax;

	private Integer customer_id;

	private String custumer_ip_address;

	private String custumer_user_agent;

	private Object billing;

	private Object shipping_address;

	private String transaction_id;

	private Date date_paid;

	private Date date_completed;

	private Array meta_data;

	private Array line_items;

	private Array tax_lines;

	private Array shipping_lines;

	private Array refunds;

	private boolean set_paid;

	/*-------------------------------------------Order - Billing properties-------------------------------------------*/

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

	/*-------------------------------------------Order - Shipping properties------------------------------------------*/

	private String shipping_first_name;

	private String shipping_last_name;

	private String shipping_company_name;

	private String shipping_address_1;

	private String shipping_address_2;

	private String shipping_city;

	private String shipping_country;

	private String shipping_postcode;

	private String shipping_email;

	private String shipping_phone;

}
