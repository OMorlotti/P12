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
public class Product
{
	private Integer id;

	private String name;

	private String slug;

	private String permalink;

	private Date date_created;

	private Date date_created_gmt;

	private Date date_modified;

	private Date date_modified_gmt;

	private String type;

	private String status;

	private String file_url;

	private Boolean featured;

	private String catalog_visibility;

	private String description;

	private String short_description;

	private String sku;

	private String price;

	private String regular_price;

	private String sale_price;

	private Date date_on_sale_from;

	private Date date_on_sale_from_gmt;

	private Date date_on_sale_to;

	private Date date_on_sale_to_gmt;

	private String price_html;

	private Boolean on_sale;

	private Boolean purchasable;

	private Integer total_sales;

	private Boolean virtual;

	private Boolean downloadable;

	private List<ProductDownloadsProperties> downloads;

	private Integer download_limit;

	private Integer download_expiry;

	private String external_url;

	private String button_text;

	private String tax_status;

	private String tax_class;

	private Boolean manage_stock;

	private Integer stock_quantity;

	private String stock_status;

	private String backorders;

	private Boolean backorders_allowed;

	private Boolean backordered;

	private Boolean sold_individually;

	private String weight;

	private ProductDimensionsProperties dimensions;

	private Boolean shipping_required;

	private Boolean shipping_taxable;

	private String shipping_class;

	private Integer shipping_class_id;

	private Boolean reviews_allowed;

	private String average_rating;

	private Integer rating_count;

	private List<Integer> related_ids;

	private List<Integer> upsell_ids;

	private List<Integer> cross_sell_ids;

	private Integer parent_id;

	private String purchase_note;

	private List<ProductCategoriesProperties> categories;

	private List<ProductTagsProperties> tags;

	private List<ProductImagesProperties> images;

	private List<ProductAttributsProperties> attributs;

	private List<ProductDefaultAttributsProperties> default_attributes;

	private List<Integer> variations;

	private List<Integer> grouped_products;

	private Integer menu_order;

	private List<ProductMetadataProperties> meta_data;
}
