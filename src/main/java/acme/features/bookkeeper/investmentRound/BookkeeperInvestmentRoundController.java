
package acme.features.bookkeeper.investmentRound;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/bookkeeper/investment-round/")
public class BookkeeperInvestmentRoundController extends AbstractController<Bookkeeper, InvestmentRound> {

	@Autowired
	private BookkeeperInvestmentRoundListWrittenService		listWrittenService;

	@Autowired
	private BookkeeperInvestmentRoundListNotWrittenService	listNotWrittenService;

	@Autowired
	private BookkeeperInvestmentRoundShowService			showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_WRITTEN, BasicCommand.LIST, this.listWrittenService);
		super.addCustomCommand(CustomCommand.LIST_NOT_WRITTEN, BasicCommand.LIST, this.listNotWrittenService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}
}
