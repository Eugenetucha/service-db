package com.programm.service_db.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programm.service_db.Exceptions.InputException;
import com.programm.service_db.Model.Entity.Buyer;
import com.programm.service_db.Model.Entity.Product;
import com.programm.service_db.Model.Entity.Purchase;
import com.programm.service_db.Model.JsonView.*;
import com.programm.service_db.Model.JsonView.Output.OutputCriteria;
import com.programm.service_db.Model.JsonView.Output.OutputJson;
import com.programm.service_db.Repository.BuyersRepository;
import com.programm.service_db.Repository.ProductsRepository;
import com.programm.service_db.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class JsonReader {
    @Autowired
    BuyersRepository buyersRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    static String input;
    public static String output;

    public void choose(List<String> type) {
        input = type.get(1);
        output = type.get(2);
        if (type.contains("search")) {
            search();
        } else {
            stat();
        }
    }

    private void search() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream input = new FileInputStream(JsonReader.input)) {
            Criterias criterias = objectMapper.readValue(input, Criterias.class);
            List<Object> criterias_list = new ArrayList<>();
            for (LinkedHashMap<String, ?> map : criterias.getCriterias()) {
                LastName lastName = new LastName();
                ProductNameAndMinTimes productNameAndMinTimes = new ProductNameAndMinTimes();
                MinAndMaxExpenses minAndMaxExpenses = new MinAndMaxExpenses();
                BadCustomers badCustomers = new BadCustomers();
                for (Map.Entry<String, ?> stringEntry : map.entrySet()) {
                    switch (stringEntry.getKey()) {
                        case "lastName":
                            lastName.setLastName(stringEntry.getValue().toString());
                            criterias_list.add(lastName);
                            break;
                        case "productName":
                            productNameAndMinTimes.setProductName(stringEntry.getValue().toString());
                            break;
                        case "minTimes":
                            productNameAndMinTimes.setMinTimes((int) stringEntry.getValue());
                            criterias_list.add(productNameAndMinTimes);
                            break;
                        case "minExpenses":
                            minAndMaxExpenses.setMinExpenses((int) stringEntry.getValue());
                            break;
                        case "maxExpenses":
                            minAndMaxExpenses.setMaxExpenses((int) stringEntry.getValue());
                            criterias_list.add(minAndMaxExpenses);
                            break;
                        case "badCustomers":
                            badCustomers.setBadCustomers((int) stringEntry.getValue());
                            criterias_list.add(badCustomers);
                            break;
                        default:
                            throw new InputException(new ExceptionJson("error", "неправильные данные json"));
                    }
                }
            }
            output(criterias_list, "search");
        } catch (IOException | InputException e) {
            throw new RuntimeException(e);
        }
    }

    private void stat() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream input = new FileInputStream(JsonReader.input)) {
            Stat stat = objectMapper.readValue(input, Stat.class);
            if (stat == null) {
                throw new InputException(new ExceptionJson("error", "неправильные данные json"));
            }
            ArrayList<Object> stats = new ArrayList<>();
            stats.add(stat);
            output(stats, "stat");
        } catch (IOException | InputException e) {
            throw new RuntimeException(e);
        }
    }

    private void output(List<Object> list, String type) throws IOException {
        OutputJson outputJson = new OutputJson();
        outputJson.setType(type);
        List<OutputCriteria> outputCriteriaList = new ArrayList<>();
        for (Object object : list) {
            OutputCriteria outputCriteria = new OutputCriteria();
            switch (object.getClass().toString()) {
                case "class com.programm.service_db.Model.JsonView.LastName":
                    LastName lastName = (LastName) object;
                    List<Long> buyers_id = buyersRepository.findBySurname(lastName.getLastName());
                    List<Buyer> buyers = buyersRepository.findAllById(buyers_id);
                    outputCriteria.setCriteria(lastName);
                    outputCriteria.setResults(buyers);
                    break;
                case "class com.programm.service_db.Model.JsonView.ProductNameAndMinTimes":
                    ProductNameAndMinTimes productNameAndMinTimes = (ProductNameAndMinTimes) object;
                    List<Product> products = productsRepository.findAll();
                    Long id = null;
                    outputCriteria.setCriteria(productNameAndMinTimes);
                    for (Product product : products) {
                        if (product.getName().contains(productNameAndMinTimes.getProductName())) {
                            id = product.getId();
                        }
                    }
                    List<Long> purchaseList = purchaseRepository.findByProductAndMaxBuy(id, (long) productNameAndMinTimes.getMinTimes());
                    List<Buyer> buyerlist = buyersRepository.findAllById(purchaseList);
                    outputCriteria.setResults(buyerlist);
                    break;
                case "class com.programm.service_db.Model.JsonView.MinAndMaxExpenses":
                    MinAndMaxExpenses minAndMaxExpenses = (MinAndMaxExpenses) object;
                    List<Long> idList = purchaseRepository.findInterval((long) minAndMaxExpenses.getMinExpenses(), (long) minAndMaxExpenses.getMaxExpenses());
                    List<Buyer> buyerList = buyersRepository.findAllById(idList);
                    outputCriteria.setCriteria(minAndMaxExpenses);
                    outputCriteria.setResults(buyerList);
                    break;
                case "class com.programm.service_db.Model.JsonView.BadCustomers":
                    BadCustomers badCustomers = (BadCustomers) object;
                    List<Long> ids = purchaseRepository.findBad((long) badCustomers.getBadCustomers());
                    outputCriteria.setCriteria(badCustomers);
                    List<Buyer> List = buyersRepository.findAllById(ids);
                    outputCriteria.setResults(Collections.singletonList(List));
                    break;
                case "class com.programm.service_db.Model.JsonView.Stat":
                    Stat stat = (Stat) object;
                    List<Long> purchases_id = purchaseRepository.date_between(stat.getStartDate(), stat.getEndDate());
                    List<Purchase> purchases = purchaseRepository.findAllById(purchases_id);
                    outputCriteria.setCriteria(stat);
                    outputCriteria.setResults(purchases);
                    break;
            }
            outputCriteriaList.add(outputCriteria);
        }
        outputJson.setResults(outputCriteriaList);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(JsonReader.output), outputJson);
    }

}
