
package acme.features.authenticated.entrepeneur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedEntrepeneurUpdateService implements AbstractUpdateService<Authenticated, Entrepeneur> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedEntrepeneurRepository repository;


	// AbstractUpdateService<Authenticated, Provider> interface ---------------

	@Override
	public boolean authorise(final Request<Entrepeneur> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Entrepeneur> request, final Entrepeneur entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		String[] activitySectors = this.repository.findCustomizationParameters().getActivitySectors().split(", ");
		request.getModel().setAttribute("activitySectors", activitySectors);

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Entrepeneur> request, final Entrepeneur entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String[] activitySectors = this.repository.findCustomizationParameters().getActivitySectors().split(", ");
		model.setAttribute("activitySectors", activitySectors);

		request.unbind(entity, model, "startUpName", "activitySector", "qualification", "skills");
	}

	@Override
	public Entrepeneur findOne(final Request<Entrepeneur> request) {
		assert request != null;

		Entrepeneur result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneEntrepeneurByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void validate(final Request<Entrepeneur> request, final Entrepeneur entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Entrepeneur> request, final Entrepeneur entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Entrepeneur> request, final Response<Entrepeneur> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
