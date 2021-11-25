package com.j01.reactor.model;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @ManyToOne
    private User user;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public TacoOrder() {
    }

    public void addTaco(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlacedAt() {
        return this.placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotBlank(message = "Delivery name is required") String getDeliveryName() {
        return this.deliveryName;
    }

    public void setDeliveryName(@NotBlank(message = "Delivery name is required") String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public @NotBlank(message = "Street is required") String getDeliveryStreet() {
        return this.deliveryStreet;
    }

    public void setDeliveryStreet(@NotBlank(message = "Street is required") String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public @NotBlank(message = "City is required") String getDeliveryCity() {
        return this.deliveryCity;
    }

    public void setDeliveryCity(@NotBlank(message = "City is required") String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public @NotBlank(message = "State is required") String getDeliveryState() {
        return this.deliveryState;
    }

    public void setDeliveryState(@NotBlank(message = "State is required") String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public @NotBlank(message = "Zip code is required") String getDeliveryZip() {
        return this.deliveryZip;
    }

    public void setDeliveryZip(@NotBlank(message = "Zip code is required") String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public @CreditCardNumber(message = "Not a valid credit card number") String getCcNumber() {
        return this.ccNumber;
    }

    public void setCcNumber(@CreditCardNumber(message = "Not a valid credit card number") String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY") String getCcExpiration() {
        return this.ccExpiration;
    }

    public void setCcExpiration(@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY") String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public @Digits(integer = 3, fraction = 0, message = "Invalid CVV") String getCcCVV() {
        return this.ccCVV;
    }

    public void setCcCVV(@Digits(integer = 3, fraction = 0, message = "Invalid CVV") String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public List<Taco> getTacos() {
        return this.tacos;
    }

    public void setTacos(List<Taco> tacos) {
        this.tacos = tacos;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TacoOrder)) return false;
        final TacoOrder other = (TacoOrder) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$placedAt = this.getPlacedAt();
        final Object other$placedAt = other.getPlacedAt();
        if (this$placedAt == null ? other$placedAt != null : !this$placedAt.equals(other$placedAt)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$deliveryName = this.getDeliveryName();
        final Object other$deliveryName = other.getDeliveryName();
        if (this$deliveryName == null ? other$deliveryName != null : !this$deliveryName.equals(other$deliveryName))
            return false;
        final Object this$deliveryStreet = this.getDeliveryStreet();
        final Object other$deliveryStreet = other.getDeliveryStreet();
        if (this$deliveryStreet == null ? other$deliveryStreet != null : !this$deliveryStreet.equals(other$deliveryStreet))
            return false;
        final Object this$deliveryCity = this.getDeliveryCity();
        final Object other$deliveryCity = other.getDeliveryCity();
        if (this$deliveryCity == null ? other$deliveryCity != null : !this$deliveryCity.equals(other$deliveryCity))
            return false;
        final Object this$deliveryState = this.getDeliveryState();
        final Object other$deliveryState = other.getDeliveryState();
        if (this$deliveryState == null ? other$deliveryState != null : !this$deliveryState.equals(other$deliveryState))
            return false;
        final Object this$deliveryZip = this.getDeliveryZip();
        final Object other$deliveryZip = other.getDeliveryZip();
        if (this$deliveryZip == null ? other$deliveryZip != null : !this$deliveryZip.equals(other$deliveryZip))
            return false;
        final Object this$ccNumber = this.getCcNumber();
        final Object other$ccNumber = other.getCcNumber();
        if (this$ccNumber == null ? other$ccNumber != null : !this$ccNumber.equals(other$ccNumber)) return false;
        final Object this$ccExpiration = this.getCcExpiration();
        final Object other$ccExpiration = other.getCcExpiration();
        if (this$ccExpiration == null ? other$ccExpiration != null : !this$ccExpiration.equals(other$ccExpiration))
            return false;
        final Object this$ccCVV = this.getCcCVV();
        final Object other$ccCVV = other.getCcCVV();
        if (this$ccCVV == null ? other$ccCVV != null : !this$ccCVV.equals(other$ccCVV)) return false;
        final Object this$tacos = this.getTacos();
        final Object other$tacos = other.getTacos();
        return this$tacos == null ? other$tacos == null : this$tacos.equals(other$tacos);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TacoOrder;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $placedAt = this.getPlacedAt();
        result = result * PRIME + ($placedAt == null ? 43 : $placedAt.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $deliveryName = this.getDeliveryName();
        result = result * PRIME + ($deliveryName == null ? 43 : $deliveryName.hashCode());
        final Object $deliveryStreet = this.getDeliveryStreet();
        result = result * PRIME + ($deliveryStreet == null ? 43 : $deliveryStreet.hashCode());
        final Object $deliveryCity = this.getDeliveryCity();
        result = result * PRIME + ($deliveryCity == null ? 43 : $deliveryCity.hashCode());
        final Object $deliveryState = this.getDeliveryState();
        result = result * PRIME + ($deliveryState == null ? 43 : $deliveryState.hashCode());
        final Object $deliveryZip = this.getDeliveryZip();
        result = result * PRIME + ($deliveryZip == null ? 43 : $deliveryZip.hashCode());
        final Object $ccNumber = this.getCcNumber();
        result = result * PRIME + ($ccNumber == null ? 43 : $ccNumber.hashCode());
        final Object $ccExpiration = this.getCcExpiration();
        result = result * PRIME + ($ccExpiration == null ? 43 : $ccExpiration.hashCode());
        final Object $ccCVV = this.getCcCVV();
        result = result * PRIME + ($ccCVV == null ? 43 : $ccCVV.hashCode());
        final Object $tacos = this.getTacos();
        result = result * PRIME + ($tacos == null ? 43 : $tacos.hashCode());
        return result;
    }

    public String toString() {
        return "TacoOrder(id=" + this.getId() + ", placedAt=" + this.getPlacedAt() + ", user=" + this.getUser() + ", deliveryName=" + this.getDeliveryName() + ", deliveryStreet=" + this.getDeliveryStreet() + ", deliveryCity=" + this.getDeliveryCity() + ", deliveryState=" + this.getDeliveryState() + ", deliveryZip=" + this.getDeliveryZip() + ", ccNumber=" + this.getCcNumber() + ", ccExpiration=" + this.getCcExpiration() + ", ccCVV=" + this.getCcCVV() + ", tacos=" + this.getTacos() + ")";
    }
}
