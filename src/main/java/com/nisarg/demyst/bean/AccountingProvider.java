package com.nisarg.demyst.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountingProvider {
  XERO("XERO"), MYOB("MYOB");
  private final String value;

  @JsonCreator
  public static AccountingProvider forValues(
      @JsonProperty String accountingProvider) {
    return AccountingProvider.valueOf(accountingProvider);
  }

  @JsonValue
  @JsonSerialize
  public String getValue() {
    return value;
  }
}
