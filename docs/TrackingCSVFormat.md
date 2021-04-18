# Tracking File Format
The user interactions are stored into CSV files having the following columns:

## File Format
- **time**: date/time in millisecond when the event was occured.
- **hitid**: ID assigned to each visit on a page.
- **deviceid**: ID of the device (phone, browser) that fired the event.
- **userid**: ID of the user who fired the event. This information is provided if user is logged in
- **page**: Name of the page where the event was fired.
- **event**: Name of the event fired. See [event list](TrackingEvent.md)
- **productid**: ID of the product associated with the event
- **value**: Value attached with the event.
- **os**: Name of the Operating System of the device that fired the event
- **osversion**: Version of the Operating System of the device that fired the event
- **devicetype**: Type of device: `mobile` or `desktop`
- **browser**: Type of browser
- **ip**: IP of the device that fired the event
- **long**: Longitude  where the event was fired
- **lat**: Latitude where the event was fired
- **traffic**: Source of the traffic.
- **referer**: URL of the referer page
- **bot**: `true` if this event was fired by a bot
- **ua**: User Agent
- **url**: URL where the event was sent
