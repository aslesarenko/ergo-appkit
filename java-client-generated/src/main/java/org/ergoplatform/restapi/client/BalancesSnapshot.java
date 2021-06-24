/*
 * Ergo Node API
 * API docs for Ergo Node. Models are shared between all Ergo products
 *
 * OpenAPI spec version: 4.0.12
 * Contact: ergoplatform@protonmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.ergoplatform.restapi.client;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import org.ergoplatform.restapi.client.Asset;
/**
 * Amount of Ergo tokens and assets
 */
@Schema(description = "Amount of Ergo tokens and assets")

public class BalancesSnapshot {
  @SerializedName("height")
  private Integer height = null;

  @SerializedName("balance")
  private Long balance = null;

  @SerializedName("assets")
  private java.util.List<Asset> assets = null;

  public BalancesSnapshot height(Integer height) {
    this.height = height;
    return this;
  }

   /**
   * Get height
   * @return height
  **/
  @Schema(required = true, description = "")
  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public BalancesSnapshot balance(Long balance) {
    this.balance = balance;
    return this;
  }

   /**
   * Get balance
   * @return balance
  **/
  @Schema(required = true, description = "")
  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }

  public BalancesSnapshot assets(java.util.List<Asset> assets) {
    this.assets = assets;
    return this;
  }

  public BalancesSnapshot addAssetsItem(Asset assetsItem) {
    if (this.assets == null) {
      this.assets = new java.util.ArrayList<Asset>();
    }
    this.assets.add(assetsItem);
    return this;
  }

   /**
   * Get assets
   * @return assets
  **/
  @Schema(description = "")
  public java.util.List<Asset> getAssets() {
    return assets;
  }

  public void setAssets(java.util.List<Asset> assets) {
    this.assets = assets;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BalancesSnapshot balancesSnapshot = (BalancesSnapshot) o;
    return Objects.equals(this.height, balancesSnapshot.height) &&
        Objects.equals(this.balance, balancesSnapshot.balance) &&
        Objects.equals(this.assets, balancesSnapshot.assets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(height, balance, assets);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BalancesSnapshot {\n");
    
    sb.append("    height: ").append(toIndentedString(height)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    assets: ").append(toIndentedString(assets)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
