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
 * Algebraic data type of sigma proposition expressions
 */
@Schema(description = "Algebraic data type of sigma proposition expressions")

public class SigmaBoolean {
  @SerializedName("op")
  private Integer op = null;

  @SerializedName("h")
  private String h = null;

  @SerializedName("g")
  private String g = null;

  @SerializedName("u")
  private String u = null;

  @SerializedName("v")
  private String v = null;

  @SerializedName("condition")
  private Boolean condition = null;

  public SigmaBoolean op(Integer op) {
    this.op = op;
    return this;
  }

   /**
   * Sigma opCode
   * @return op
  **/
  @Schema(required = true, description = "Sigma opCode")
  public Integer getOp() {
    return op;
  }

  public void setOp(Integer op) {
    this.op = op;
  }

  public SigmaBoolean h(String h) {
    this.h = h;
    return this;
  }

   /**
   * Get h
   * @return h
  **/
  @Schema(description = "")
  public String getH() {
    return h;
  }

  public void setH(String h) {
    this.h = h;
  }

  public SigmaBoolean g(String g) {
    this.g = g;
    return this;
  }

   /**
   * Get g
   * @return g
  **/
  @Schema(description = "")
  public String getG() {
    return g;
  }

  public void setG(String g) {
    this.g = g;
  }

  public SigmaBoolean u(String u) {
    this.u = u;
    return this;
  }

   /**
   * Get u
   * @return u
  **/
  @Schema(description = "")
  public String getU() {
    return u;
  }

  public void setU(String u) {
    this.u = u;
  }

  public SigmaBoolean v(String v) {
    this.v = v;
    return this;
  }

   /**
   * Get v
   * @return v
  **/
  @Schema(description = "")
  public String getV() {
    return v;
  }

  public void setV(String v) {
    this.v = v;
  }

  public SigmaBoolean condition(Boolean condition) {
    this.condition = condition;
    return this;
  }

   /**
   * Get condition
   * @return condition
  **/
  @Schema(description = "")
  public Boolean isCondition() {
    return condition;
  }

  public void setCondition(Boolean condition) {
    this.condition = condition;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SigmaBoolean sigmaBoolean = (SigmaBoolean) o;
    return Objects.equals(this.op, sigmaBoolean.op) &&
        Objects.equals(this.h, sigmaBoolean.h) &&
        Objects.equals(this.g, sigmaBoolean.g) &&
        Objects.equals(this.u, sigmaBoolean.u) &&
        Objects.equals(this.v, sigmaBoolean.v) &&
        Objects.equals(this.condition, sigmaBoolean.condition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(op, h, g, u, v, condition);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SigmaBoolean {\n");
    
    sb.append("    op: ").append(toIndentedString(op)).append("\n");
    sb.append("    h: ").append(toIndentedString(h)).append("\n");
    sb.append("    g: ").append(toIndentedString(g)).append("\n");
    sb.append("    u: ").append(toIndentedString(u)).append("\n");
    sb.append("    v: ").append(toIndentedString(v)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
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
