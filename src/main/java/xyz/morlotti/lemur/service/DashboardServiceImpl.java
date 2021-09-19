package xyz.morlotti.lemur.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.morlotti.woocommerce.client.proxy.WooCommerceClients;

@Service
public class DashboardServiceImpl implements DashboardService
{
	@Autowired
	private WooCommerceClients wooCommerceClients;

}
