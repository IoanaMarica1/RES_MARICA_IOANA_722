import controller.ConsoleController;
import service.AstronautService;
import service.MissionEventService;
import service.SupplyService;
;


public class Main {
    public static void main(String[] args) {
        var controller = new ConsoleController(
               new AstronautService(),
                new MissionEventService(),
                new SupplyService()
        );
        controller.run();
    }
}