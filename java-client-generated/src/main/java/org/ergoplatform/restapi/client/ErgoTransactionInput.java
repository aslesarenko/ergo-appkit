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
import org.ergoplatform.restapi.client.SpendingProof;
/**
 * ErgoTransactionInput
 */


public class ErgoTransactionInput {
  @SerializedName("boxId")
  private String boxId = null;

  @SerializedName("spendingProof")
  private SpendingProof spendingProof = null;

  @SerializedName("extension")
  private java.util.Map<String, String> extension = null;

  public ErgoTransactionInput boxId(String boxId) {
    this.boxId = boxId;
    return this;
  }

   /**
   * Get boxId
   * @return boxId
  **/
  @Schema(required = true, description = "")
  public String getBoxId() {
    return boxId;
  }

  public void setBoxId(String boxId) {
    this.boxId = boxId;
  }

  public ErgoTransactionInput spendingProof(SpendingProof spendingProof) {
    this.spendingProof = spendingProof;
    return this;
  }

   /**
   * Get spendingProof
   * @return spendingProof
  **/
  @Schema(required = true, description = "")
  public SpendingProof getSpendingProof() {
    return spendingProof;
  }

  public void setSpendingProof(SpendingProof spendingProof) {
    this.spendingProof = spendingProof;
  }

  public ErgoTransactionInput extension(java.util.Map<String, String> extension) {
    this.extension = extension;
    return this;
  }

  public ErgoTransactionInput putExtensionItem(String key, String extensionItem) {
    if (this.extension == null) {
      this.extension = new java.util.HashMap<String, String>();
    }
    this.extension.put(key, extensionItem);
    return this;
  }

   /**
   * Get extension
   * @return extension
  **/
  @Schema(example = "{\"1\":\"a2aed72ff1b139f35d1ad2938cb44c9848a34d4dcfd6d8ab717ebde40a7304f2541cf628ffc8b5c496e6161eba3f169c6dd440704b1719e0\"}", description = "")
  public java.util.Map<String, String> getExtension() {
    return extension;
  }

  public void setExtension(java.util.Map<String, String> extension) {
    this.extension = extension;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErgoTransactionInput ergoTransactionInput = (ErgoTransactionInput) o;
    return Objects.equals(this.boxId, ergoTransactionInput.boxId) &&
        Objects.equals(this.spendingProof, ergoTransactionInput.spendingProof) &&
        Objects.equals(this.extension, ergoTransactionInput.extension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boxId, spendingProof, extension);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErgoTransactionInput {\n");
    
    sb.append("    boxId: ").append(toIndentedString(boxId)).append("\n");
    sb.append("    spendingProof: ").append(toIndentedString(spendingProof)).append("\n");
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
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
