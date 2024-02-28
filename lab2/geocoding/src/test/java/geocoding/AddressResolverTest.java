package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    AddressResolverService resolver;

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        ISimpleHttpClient httpClient = Mockito.mock(ISimpleHttpClient.class);
        resolver = new AddressResolverService(httpClient);

        String jsonString = "{\n" +
                "\t\"info\": {\n" +
                "\t\t\"statuscode\": 0,\n" +
                "\t\t\"copyright\": {\n" +
                "\t\t\t\"text\": \"\u00a9 2024 MapQuest, Inc.\",\n" +
                "\t\t\t\"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "\t\t\t\"imageAltText\": \"\u00a9 2024 MapQuest, Inc.\"\n" +
                "\t\t},\n" +
                "\t\t\"messages\": []\n" +
                "\t},\n" +
                "\t\"options\": {\n" +
                "\t\t\"maxResults\": 1,\n" +
                "\t\t\"ignoreLatLngInput\": false\n" +
                "\t},\n" +
                "\t\"results\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"providedLocation\": {\n" +
                "\t\t\t\t\"latLng\": {\n" +
                "\t\t\t\t\t\"lat\": 40.63436,\n" +
                "\t\t\t\t\t\"lng\": -8.65616\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"locations\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"street\": \"Avenida da Universidade\",\n" +
                "\t\t\t\t\t\"adminArea6\": \"Aveiro\",\n" +
                "\t\t\t\t\t\"adminArea6Type\": \"Neighborhood\",\n" +
                "\t\t\t\t\t\"adminArea5\": \"Aveiro\",\n" +
                "\t\t\t\t\t\"adminArea5Type\": \"City\",\n" +
                "\t\t\t\t\t\"adminArea4\": \"Aveiro\",\n" +
                "\t\t\t\t\t\"adminArea4Type\": \"County\",\n" +
                "\t\t\t\t\t\"adminArea3\": \"\",\n" +
                "\t\t\t\t\t\"adminArea3Type\": \"State\",\n" +
                "\t\t\t\t\t\"adminArea1\": \"PT\",\n" +
                "\t\t\t\t\t\"adminArea1Type\": \"Country\",\n" +
                "\t\t\t\t\t\"postalCode\": \"3810-489\",\n" +
                "\t\t\t\t\t\"geocodeQualityCode\": \"B1AAA\",\n" +
                "\t\t\t\t\t\"geocodeQuality\": \"STREET\",\n" +
                "\t\t\t\t\t\"dragPoint\": false,\n" +
                "\t\t\t\t\t\"sideOfStreet\": \"L\",\n" +
                "\t\t\t\t\t\"linkId\": \"0\",\n" +
                "\t\t\t\t\t\"unknownInput\": \"\",\n" +
                "\t\t\t\t\t\"type\": \"s\",\n" +
                "\t\t\t\t\t\"latLng\": {\n" +
                "\t\t\t\t\t\t\"lat\": 40.63437,\n" +
                "\t\t\t\t\t\t\"lng\": -8.65625\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"displayLatLng\": {\n" +
                "\t\t\t\t\t\t\"lat\": 40.63437,\n" +
                "\t\t\t\t\t\t\"lng\": -8.65625\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"mapUrl\": \"\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        when(httpClient.doHttpGet(anyString())).thenReturn(jsonString);

        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        verify(httpClient, times(1)).doHttpGet(anyString());

        //return
        Address expected = new Address("Avenida da Universidade", "Aveiro", "3810-489", "");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        ISimpleHttpClient httpClient = Mockito.mock(ISimpleHttpClient.class);
        resolver = new AddressResolverService(httpClient);

        String jsonString = "{\n" +
                "\t\"info\": {\n" +
                "\t\t\"statuscode\": 400,\n" +
                "\t\t\"copyright\": {\n" +
                "\t\t\t\"text\": \"\u00a9 2024 MapQuest, Inc.\",\n" +
                "\t\t\t\"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "\t\t\t\"imageAltText\": \"\u00a9 2024 MapQuest, Inc.\"\n" +
                "\t\t},\n" +
                "\t\t\"messages\": [\n" +
                "\t\t\t\"Illegal argument from request: Invalid LatLng specified.\"\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"options\": {\n" +
                "\t\t\"maxResults\": 1,\n" +
                "\t\t\"ignoreLatLngInput\": false\n" +
                "\t},\n" +
                "\t\"results\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"providedLocation\": {},\n" +
                "\t\t\t\"locations\": []\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        when(httpClient.doHttpGet(anyString())).thenReturn(jsonString);

        Optional<Address> result = resolver.findAddressForLocation(-361, -361);
        verify(httpClient, times(1)).doHttpGet(anyString());
        // verify no valid result
        assertFalse(result.isPresent());

    }
}