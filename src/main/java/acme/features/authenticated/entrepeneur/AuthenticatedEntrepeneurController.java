
package acme.features.authenticated.entrepeneur;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.Entrepeneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/entrepeneur/")
public class AuthenticatedEntrepeneurController extends AbstractController<Authenticated, Entrepeneur> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedEntrepeneurCreateService	createService;

	@Autowired
	private AuthenticatedEntrepeneurUpdateService	updateService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}
