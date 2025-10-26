package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n’imprime pas leticket si le montant inséré est insuffisant
	void ticketNotPrintedWhenBalanceIsInsufficient() {
		machine.insertMoney(PRICE - 1);
		assertFalse( machine.printTicket(), "le montant inséré est suffisant");
	}

	@Test
	// S4 : on imprime si le montant inséré est suffisant
	void ticketPrintedWhenBalanceIsSufficient() {
		machine.insertMoney(PRICE);
		assertTrue( machine.printTicket(), "le montant inséré est insuffisant");
	}

	@Test
	// S5 : la balance est décrémentée du prix du ticket après impression
	void balanceDecrementedAfterPrintingTicket() {
		machine.insertMoney(PRICE + 1); 
		machine.printTicket(); 
		assertEquals(1, machine.getBalance(), "la balance n'est pas correctement décrémentée");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void collectedAmountIsUpdatedAfterPrintingTicket() {
		machine.insertMoney(PRICE + 1);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "le montant collecté n'est pas correctement mis à jour");
	}

	@Test
	// S7 : refund()rend correctement la monnaie
	void refundReturnsCorrectBalance() {
		machine.insertMoney(PRICE + 100);
		machine.printTicket();
		int refund = machine.refund();
		assertEquals(100, refund, "refund() devrait rendre tout l'argent inséré");
	}

	@Test
	// S8 : après refund() la balance est remise à zéro
	void balanceIsZeroAfterRefund() {
		machine.insertMoney(PRICE + 100);
		machine.printTicket();
		machine.refund();
		assertEquals(0, machine.getBalance(), "la balance devrait être remise à zéro après refund()");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10),
                "L'insertion d'un montant négatif devrait provoquer une exception");
	}

	@Test
	// S10 :on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateMachineWithNegativePrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1),
                "La création d'une machine avec un prix négatif devrait provoquer une exception");
	}
	}



