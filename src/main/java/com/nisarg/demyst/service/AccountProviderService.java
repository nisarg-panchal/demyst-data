package com.nisarg.demyst.service;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisarg.demyst.bean.MonthlyBalance;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProviderService {

  public List<MonthlyBalance> getBalanceSheetData(String provider) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    MonthlyBalance[] monthlyBalances = mapper.readValue(
        new ClassPathResource(provider.toLowerCase() + ".json").getFile(), MonthlyBalance[].class);
    return new ArrayList<>(Arrays.asList(monthlyBalances));
  }
}
