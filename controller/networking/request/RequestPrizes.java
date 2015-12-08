package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponsePrizes;

public class RequestPrizes extends GameRequest {
	
	private ResponsePrizes responsePrizes;
	
	public RequestPrizes() {
        responses.add(responsePrizes = new ResponsePrizes());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the prizes business here
		responsePrizes.setPrize(client.getPlayer().getLastPrize());
	}

}
