package vttp.nusiss.MarchClass.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Controller
@RequestMapping("/poke")
public class PokemonController {
    
    @GetMapping
    public String getPokemon(
        @RequestParam String pokemonName,
        Model model){

            String url = "https://pokeapi.co/api/v2/pokemon/"+pokemonName;

		RequestEntity<Void> req = RequestEntity
			.get(url)
			.accept(MediaType.APPLICATION_JSON)
			.build();

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> resp = template.exchange(req, String.class);
		HttpHeaders headers = resp.getHeaders();
		try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject data = reader.readObject();
           
			String url2 = data.getJsonObject("sprites")
                .getJsonObject("versions")
                .getJsonObject("generation-ii")
                .getJsonObject("crystal")
                .getString("front_default");

            String url3 = data.getJsonObject("sprites")
                .getJsonObject("versions")
                .getJsonObject("generation-ii")
                .getJsonObject("crystal")
                .getString("back_default");
                
            model.addAttribute("data",data);
            model.addAttribute("url2",url2);
            model.addAttribute("url3",url3);
        }catch(IOException ex){
                
            }
			
		// for(Entry<String,List<String>> h : headers.entrySet()){
		// 	System.out.printf("header: %s value:%s".formatted(h.getKey(),h.getValue()));
		// }
		

		// System.out.printf("Status code: %d\n", resp.getStatusCodeValue());
		// System.out.printf("Payload: %d\n", resp.getBody());
      
        model.addAttribute("pokemonName",pokemonName);

            return "result";
        }

		
}
