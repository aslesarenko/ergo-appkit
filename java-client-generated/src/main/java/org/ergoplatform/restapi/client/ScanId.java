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
/**
 * ScanId
 */


public class ScanId {
  @SerializedName("scanId")
  private Integer scanId = null;

  public ScanId scanId(Integer scanId) {
    this.scanId = scanId;
    return this;
  }

   /**
   * Get scanId
   * @return scanId
  **/
  @Schema(description = "")
  public Integer getScanId() {
    return scanId;
  }

  public void setScanId(Integer scanId) {
    this.scanId = scanId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScanId scanId = (ScanId) o;
    return Objects.equals(this.scanId, scanId.scanId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scanId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScanId {\n");
    
    sb.append("    scanId: ").append(toIndentedString(scanId)).append("\n");
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
