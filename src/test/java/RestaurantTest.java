import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class RestaurantTest {
	
	Restaurant restaurant;

	@BeforeEach
	public void mockRestaurantObject() {
		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.parse("22:00:00");
		restaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai", openingTime, closingTime));
		restaurant.addToMenu("Sweet corn soup", 119);
		restaurant.addToMenu("Vegetable lasagne", 269);
	}
		
	
    // spy mockito helper method to mock opening and closing times for the below two test cases 
	private void mockCurrentTime(LocalTime mockTime) {
		when(restaurant.getCurrentTime()).thenReturn(mockTime);
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
		LocalTime mockTime = LocalTime.parse("12:30:00");
		mockCurrentTime(mockTime);
		assertTrue(restaurant.isRestaurantOpen());
	}

	@Test
	public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
		LocalTime mockTime = LocalTime.parse("23:30:00");
		mockCurrentTime(mockTime);
		assertFalse(restaurant.isRestaurantOpen());
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	public void adding_item_to_menu_should_increase_menu_size_by_1() {

		int initialMenuSize = restaurant.getMenu().size();
		restaurant.addToMenu("Sizzling brownie", 319);
		assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

		int initialMenuSize = restaurant.getMenu().size();
		restaurant.removeFromMenu("Vegetable lasagne");
		assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_that_does_not_exist_should_throw_exception() {

		assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
	}
	// <<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}