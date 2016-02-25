package gov.va.vba.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import gov.va.vba.AbstractIntegrationTest;
import gov.va.vba.domain.reference.Lookup;
import gov.va.vba.service.data.LookupDataService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class LookupResourceTest extends AbstractIntegrationTest {

	private static final String DEFAULT_NAME = "SAMPLE_TEXT";
	private static final String UPDATED_NAME = "UPDATED_TEXT";

	private static final Integer DEFAULT_VERSION = 0;
	private static final Integer UPDATED_VERSION = 1;

	@Inject
	private LookupDataService lookupDataService;

	private MockMvc restLookupMockMvc;

	private Lookup lookup;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		LookupResource lookupResource = new LookupResource();
		ReflectionTestUtils.setField(lookupResource, "lookupDataService",	lookupDataService);
		this.restLookupMockMvc = MockMvcBuilders.standaloneSetup(lookupResource).build();
	}

	@Before
	public void initTest() {
		lookup = new Lookup();
		lookup.setName(DEFAULT_NAME);
		lookup.setVersion(DEFAULT_VERSION);
	}

	/*@Test
	public void createLookup() throws Exception {							//Test disabled because not supported in the LookupResource
		long databaseSizeBeforeCreate = lookupDataService.count();

		// Create the Lookup
		restLookupMockMvc.perform(
				post("/api/lookup").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(lookup)))
						.andExpect(status().isCreated());

		// Validate the Lookup in the database
		long lookupsAfterCreate = lookupDataService.count();
		Lookup testLookup = lookupDataService.findOne(lookup.getId());

		assertThat(lookupsAfterCreate).isEqualTo(databaseSizeBeforeCreate + 1);
		assertThat(testLookup.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testLookup.getVersion()).isEqualTo(DEFAULT_VERSION);
	}*/

	@Test
	public void getAllLookups() throws Exception {
		// Initialize the database
		
		// Get all the Lookups
		lookup = lookupDataService.save(lookup);

        // Get all the facts
        restLookupMockMvc.perform(get("/api/lookup"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())));
	}

	/*@Test 														//Test disabled because not supported in the LookupResource
	public void getLookup() throws Exception {
		// Initialize the database
		lookup = lookupDataService.save(lookup);

		// Get the Lookup
		restLookupMockMvc.perform(get("/api/lookup/{id}", lookup.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(lookup.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
	}*/

	/*@Test															//Test disabled because not supported in the LookupResource
	public void getNonExistingLookup() throws Exception {
		// Get the Lookup
		restLookupMockMvc.perform(get("/api/lookup/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}*/

	/*@Test															//Test disabled because not supported in the LookupResource
	public void updateLookup() throws Exception {
		// Initialize the database
		lookup = lookupDataService.save(lookup);

		long databaseSizeBeforeUpdate = lookupDataService.count();

		// Update the Lookup
		lookup.setName(UPDATED_NAME);
		lookup.setVersion(UPDATED_VERSION);

		restLookupMockMvc.perform(put("/api/lookup")
						.contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(lookup)))
						.andExpect(status().isOk());

		// Validate the Lookup in the database
		long noOfLookupsAfterUpdate = lookupDataService.count();
		Lookup testLookup = lookupDataService.findOne(lookup.getId());

		assertThat(noOfLookupsAfterUpdate).isEqualTo(databaseSizeBeforeUpdate);
		assertThat(testLookup.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testLookup.getVersion()).isEqualTo(UPDATED_VERSION);
	}*/

	/*@Test														//Test disabled because not supported in the LookupResource
	public void deleteLookup() throws Exception {
		// Initialize the database
		lookup = lookupDataService.save(lookup);

		long databaseSizeBeforeDelete = lookupDataService.count();

		// Get the Lookup
		restLookupMockMvc.perform(
				delete("/api/lookup/{id}", lookup.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		long noOfLookupsAfterDelete = lookupDataService.count();
		assertThat(noOfLookupsAfterDelete).isEqualTo(databaseSizeBeforeDelete - 1);
	}*/
}
