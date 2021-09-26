package xyz.morlotti.lemur.clients.woocommerce.bean;

import lombok.*;

import java.util.Date;
import java.util.List;

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

	private String created_via;

	private String version;

	private String status;

	private String currency;

	private Date date_created;

	private Date date_created_gmt;

	private Date date_modified;

	private Date date_modified_gmt;

	private String discount_total;

	private String discount_tax;

	private String shipping_total;

	private String shipping_tax;

	private String cart_tax;

	private String total;

	private String total_tax;

	private Boolean prices_include_tax;

	private Integer customer_id;

	private String custumer_ip_address;

	private String custumer_user_agent;

	private String customer_note;

	private OrderBillingProperties billing;

	private OrderShippingProperties shipping;

	private String payment_method;

	private String payment_method_title;

	private String transaction_id;

	private Date date_paid;

	private Date date_paid_gmt;

	private Date date_completed;

	private Date date_completed_gmt;

	private String cart_hash;

	private List<OrderMetadataProperties> meta_data;

	private List<OrderLineItemsProperties> line_items;

	private List<OrderTaxLinesProperties> tax_lines;

	private List<OrderShippingLinesProperties> shipping_lines;

	private List<OrderCouponLinesProperties> coupons_lines;

	private List<OrderFeeLinesProperties> fee_lines;

	private List<OrderRefundsProperties> refunds;

	private boolean set_paid;

	/*-------------------------------------------Order - Shipping properties------------------------------------------*/



}
