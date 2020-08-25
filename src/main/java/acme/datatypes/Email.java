
package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.DomainDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Email extends DomainDatatype {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@#$€%ˆ&*(){}|~<>;:[\\]]*$")
	private String				displayName;

	@NotBlank
	private String				user;

	@NotBlank
	private String				domain;


	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();

		if (this.displayName != null) {
			result.append(this.displayName);
			result.append(" <");
		}

		result.append(this.user);
		result.append("@");
		result.append(this.domain);

		if (this.displayName != null) {
			result.append(">");
		}

		return result.toString();
	}

}
