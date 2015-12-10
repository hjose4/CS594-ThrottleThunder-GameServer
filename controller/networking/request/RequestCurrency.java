package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponseCurrency;

public class RequestCurrency extends GameRequest {
	
	private ResponseCurrency responseCurrency;
	
	public RequestCurrency() {
        responses.add(responseCurrency = new ResponseCurrency());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the rankings business here
		responseCurrency.setCurrency(client.getPlayer().getCurrency());
	}

}
