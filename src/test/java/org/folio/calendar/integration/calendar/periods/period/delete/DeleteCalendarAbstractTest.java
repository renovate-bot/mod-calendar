package org.folio.calendar.integration.calendar.periods.period.delete;

import io.restassured.response.Response;
import java.util.UUID;
import org.folio.calendar.integration.calendar.periods.AbstractExistingCalendarTest;

/**
 * Setup the following service points for use in tests which may rely on them
 * <ul>
 *   <li>On service point 0:</li>
 *   <li>
 *     <ul>
 *       <li>Calendar  2021-01-01 to 2021-04-30 ({@code PERIOD_FULL_EXAMPLE_F}, ID A)</li>
 *       <li>Calendar  2021-05-01 to 2021-09-22 ({@code PERIOD_FULL_EXAMPLE_G}, ID B)</li>
 *       <li>Exception 2021-03-16 to 2021-04-30 ({@code PERIOD_FULL_EXCEPTIONAL_F}, ID F)</li>
 *       <li>Exception 2021-07-04 to 2021-09-22 ({@code PERIOD_FULL_EXCEPTIONAL_G}, ID 0)</li>
 *     </ul>
 *   </li>
 *   <li>On service point 1:</li>
 *   <li>
 *     <ul>
 *       <li>Calendar  2021-05-01 to 2021-09-22 ({@code PERIOD_FULL_EXAMPLE_D}, ID D)</li>
 *     </ul>
 *   </li>
 *   <li>On service point C:</li>
 *   <li>
 *     <ul>
 *       <li>Exception 2021-01-01 to 2021-01-04 ({@code PERIOD_FULL_EXCEPTIONAL_C}, ID C)</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public abstract class DeleteCalendarAbstractTest extends AbstractExistingCalendarTest {

  public static final String DELETE_CALENDAR_API_ROUTE = "/calendar/periods/%s/period/%s";
  public static final String GET_CALENDAR_API_ROUTE = "/calendar/periods/%s/period/%s";

  protected Response sendDeleteRequest(UUID servicePointId, UUID calendarId) {
    return ra()
      .delete(getRequestUrl(String.format(DELETE_CALENDAR_API_ROUTE, servicePointId, calendarId)));
  }

  protected Response sendGetRequest(UUID servicePointId, UUID calendarId) {
    return ra()
      .get(getRequestUrl(String.format(GET_CALENDAR_API_ROUTE, servicePointId, calendarId)));
  }
}