package com.coindesk.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.coindesk.entity.CoindeskCoins;
import com.coindesk.entity.CoindeskCoinsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CoindeskController {
	private final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	private final CoindeskCoinsRepository coindeskCoinsRepository;
	
    @Autowired
    public CoindeskController(CoindeskCoinsRepository coindeskCoinsRepository) {
        this.coindeskCoinsRepository = coindeskCoinsRepository;
    }
	
	//coindesk api 取得
    @GetMapping("/coindeskapi")
    public String getBitcoinPrice(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(COINDESK_API_URL, String.class);
        model.addAttribute("result", result);
        return "index";
    }
    
	//coindesk api 取得
    @GetMapping("/coindeskapiformat")
    public String getCoinFormation(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(COINDESK_API_URL, String.class);
        CoindeskCoins coin;
        List<Map<String, String>> currencyList = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result);
            
            // 取得時間節點並且格式化
            String updatedString = rootNode.get("time").get("updated").asText();
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date updatedDate = inputFormat.parse(updatedString);
            String formattedUpdated = outputFormat.format(updatedDate);
            // 取得價格訊息
            JsonNode bpiNode = rootNode.get("bpi");
            
            // 取得json node中需要的資訊，匯率和幣別，並且查找中文名稱
            for (JsonNode currencyNode : bpiNode) {
            	Map<String, String> currency = new HashMap<>();
                String currencyCode = currencyNode.get("code").asText();
                String currencyRate = currencyNode.get("rate").asText();
                coin = coindeskCoinsRepository.findByCname(currencyCode);
                currency.put("code", currencyCode);
                currency.put("rate", currencyRate);
                currency.put("chz", coin.getChzcname());
                currencyList.add(currency);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("resultformat", result);
        model.addAttribute("currencyList", currencyList);
        return "index";
    }
    
    @PostMapping("/coins")
    public String addCoin(@RequestParam String cName, @RequestParam String chzCName, Model model) {
    	try {
    	       coindeskCoinsRepository.save(new CoindeskCoins(cName, chzCName));
        } catch (Exception e) {
               e.printStackTrace();
               model.addAttribute("coin", "出現錯誤，請詢問客服");
               return "index";
        }
        model.addAttribute("response", "ok");
        return "index";
    }
    
    //以id, 中英文名稱查詢幣種
    @GetMapping("/coins")
    public String getCoin(@RequestParam String input, Model model) {
        CoindeskCoins coin;
        try {
        if (input.matches("\\d+")) { // 輸入id的情況
            int id = Integer.parseInt(input);
            coin = coindeskCoinsRepository.findById(id).orElse(null);
            //找不到的情況，有可能英文名稱是數字
            if (coin == null) {
            	coin = coindeskCoinsRepository.findByCname(input);
            }
        } else { // 輸入 cName 或 chzCName
            if (Character.UnicodeScript.of(input.charAt(0)) == Character.UnicodeScript.HAN) {
                // 若輸入繁體中文，使用 findByChzCName
                coin = coindeskCoinsRepository.findByChzcname(input);
            } else {
                // 非輸入繁體中文使用 findByCName
                coin = coindeskCoinsRepository.findByCname(input);
            }
        }
        if (coin == null) {
        	coin = new CoindeskCoins();
        	coin.setId(0);
        	coin.setCname("no found, or you can add one.");
        	coin.setChzcname("請重新查詢或進行新增");
        }
        model.addAttribute("coin", coin);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("coin", "出現錯誤，請詢問客服");
            return "index";
        }

        return "index";
    }

    @PostMapping("/coins/updateCoins")
    public String updateCoin(@RequestParam String inputupdate, @RequestParam String update, Model model) {
        CoindeskCoins coin;
        int flag =0;
        try {
        //無法更改ID，輸入數字視為更改英文名稱
        if (Character.UnicodeScript.of(inputupdate.charAt(0)) == Character.UnicodeScript.HAN) {
            // 若輸入繁體中文，使用 findByChzCName
            coin = coindeskCoinsRepository.findByChzcname(inputupdate);
            flag=1;
        } else {
            // 非輸入繁體中文使用 findByCName
            coin = coindeskCoinsRepository.findByCname(inputupdate);
            flag=2;
        }
        
        if (coin != null) {
            if (flag == 1) {
            	coin.setChzcname(update);       	
            } else if (flag == 2) {
            	coin.setCname(update);
            } 
            coindeskCoinsRepository.save(coin);
            model.addAttribute("update_response", "ok");
        }else {
        	model.addAttribute("update_response", "查找不到欄位，請重新輸入");
        }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("coin", "出現錯誤，請詢問客服");
            return "index";
        }
        
        return "index";
    }

    @PostMapping("/coins/deleteCoins")
    public String deleteCoin(@RequestParam String inputdelete, Model model) {
            CoindeskCoins coin;
            int id = Integer.parseInt(inputdelete);
            try {
            coin = coindeskCoinsRepository.findById(id).orElse(null);
            if (coin != null) {               
                coin.setChzcname("");       	             
                coin.setCname("");              
                coindeskCoinsRepository.save(coin);
                model.addAttribute("delete_response", "ok");
            }else {
            	model.addAttribute("delete_response", "查找不到欄位，請重新輸入");
            }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("coin", "出現錯誤，請詢問客服");
                return "index";
            }
        return "index";
    }
	
}
