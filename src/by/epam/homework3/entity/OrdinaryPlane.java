package by.epam.homework3.entity;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class OrdinaryPlane extends Plane {
    private XMLGregorianCalendar date;
    private BigDecimal price;

    public OrdinaryPlane() {
        super();
    }

    public OrdinaryPlane(XMLGregorianCalendar date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        OrdinaryPlane that = (OrdinaryPlane) o;

        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrdinaryPlane{");
        sb.append(super.toString());
        sb.append("date=").append(date);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
