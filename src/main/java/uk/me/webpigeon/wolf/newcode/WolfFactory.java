package uk.me.webpigeon.wolf.newcode;

import uk.me.webpigeon.wolf.newcode.actions.WolfUtils;
import uk.me.webpigeon.wolf.newcode.legacy.LegacyUtils;
import uk.me.webpigeon.wolf.newcode.players.AbstractPlayer;
import uk.me.webpigeon.wolf.newcode.players.behavours.BehavourPlayer;
import uk.me.webpigeon.wolf.newcode.players.behavours.DebugAnnounceRole;
import uk.me.webpigeon.wolf.newcode.players.behavours.EatSomeone;
import uk.me.webpigeon.wolf.newcode.players.behavours.LieAboutRole;
import uk.me.webpigeon.wolf.newcode.players.behavours.LynchPrioityTargets;
import uk.me.webpigeon.wolf.newcode.players.behavours.RandomUnsafeLynch;
import uk.me.webpigeon.wolf.newcode.players.behavours.SeerSavingAnnounce;

public class WolfFactory {
	
	private WolfFactory() {
		
	}
	
	public static WolfController buildGame() {
		WolfModel model = new WolfModel();
		WolfController controller = new WolfController(model);

		controller.addPlayer("Fred_bh", buildBehavourPlayer());
		controller.addPlayer("John_bh", buildBehavourPlayer());
		controller.addPlayer("Bob_bh", buildBehavourPlayer());
		controller.addPlayer("Wolfgang_bh", buildBehavourPlayer());
		controller.addPlayer("Pebbles_bh", buildBehavourPlayer());
		controller.addPlayer("Jackie_bh", buildBehavourPlayer());
		controller.addPlayer("Jess_bh", buildBehavourPlayer());
		controller.addPlayer("Sarah_bh", buildBehavourPlayer());
		
		return controller;
	}
	
	public static SessionManager buildBehavourPlayer() {
		BehavourPlayer player = new BehavourPlayer();
		player.addBehavour(new SeerSavingAnnounce());
		player.addBehavour(new DebugAnnounceRole());
		player.addBehavour(new LieAboutRole());
		player.addBehavour(new EatSomeone());
		player.addBehavour(new LynchPrioityTargets("wolf", "seer"));
		player.addBehavour(new LynchPrioityTargets("villager", "wolf"));
		player.addBehavour(new LynchPrioityTargets("seer", "wolf"));
		player.addBehavour(new RandomUnsafeLynch());
		
		Thread t = new Thread(player);
		t.setName("behavour-"+System.currentTimeMillis());
		t.start();
		
		return player;
	}
	
	public static SessionManager wrap(String threadName, GameListener listener) {
		Event2Listener event = new Event2Listener(listener);
		
		Thread t = new Thread(event);
		t.setName(threadName);
		t.start();
		
		return event;
	}

}
