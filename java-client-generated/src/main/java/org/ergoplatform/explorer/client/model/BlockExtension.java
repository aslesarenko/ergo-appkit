/*
 * Ergo blockchain explorer
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 2.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.ergoplatform.explorer.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.ergoplatform.explorer.client.model.KeyValueItem;
/**
 * BlockExtension
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-12-09T08:14:50.203Z[GMT]")
public class BlockExtension {
  @SerializedName("headerId")
  private String headerId = null;

  @SerializedName("digest")
  private String digest = null;

  @SerializedName("fields")
  private List<KeyValueItem> fields = new ArrayList<>();

  public BlockExtension headerId(String headerId) {
    this.headerId = headerId;
    return this;
  }

   /**
   * Get headerId
   * @return headerId
  **/
  @Schema(required = true, description = "")
  public String getHeaderId() {
    return headerId;
  }

  public void setHeaderId(String headerId) {
    this.headerId = headerId;
  }

  public BlockExtension digest(String digest) {
    this.digest = digest;
    return this;
  }

   /**
   * Get digest
   * @return digest
  **/
  @Schema(required = true, description = "")
  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public BlockExtension fields(List<KeyValueItem> fields) {
    this.fields = fields;
    return this;
  }

  public BlockExtension addFieldsItem(KeyValueItem fieldsItem) {
    this.fields.add(fieldsItem);
    return this;
  }

   /**
   * List of key-value records
   * @return fields
  **/
  @Schema(required = true, description = "List of key-value records")
  public List<KeyValueItem> getFields() {
    return fields;
  }

  public void setFields(List<KeyValueItem> fields) {
    this.fields = fields;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlockExtension blockExtension = (BlockExtension) o;
    return Objects.equals(this.headerId, blockExtension.headerId) &&
        Objects.equals(this.digest, blockExtension.digest) &&
        Objects.equals(this.fields, blockExtension.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(headerId, digest, fields);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BlockExtension {\n");
    
    sb.append("    headerId: ").append(toIndentedString(headerId)).append("\n");
    sb.append("    digest: ").append(toIndentedString(digest)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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