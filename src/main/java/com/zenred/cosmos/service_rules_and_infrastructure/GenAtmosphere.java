package com.zenred.cosmos.service_rules_and_infrastructure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.Atmosphere;
import com.zenred.cosmos.domain.AtmosphereDao;
import com.zenred.cosmos.domain.AtmosphereParts;
import com.zenred.cosmos.domain.Planetoid;
import com.zenred.cosmos.domain.Star;
import com.zenred.cosmos.domain.StarFactory;
import com.zenred.cosmos.domain.StarToChemicalProfile;
import com.zenred.util.GenRandomRolls;

/**
 * 
 * @author jredden
 *
 *
 *         key with three dimensions Star color Planetoid Temperature Planetoid
 *         Radius in kilometers
 *
 *
 */

public class GenAtmosphere {

	private static Logger logger = Logger.getLogger(GenAtmosphere.class);

	interface AtmosphereResolutionIF {
		List<Atmosphere> resolve(StarFactory starFactory);
	}

	protected static List<Atmosphere> normalize(List<Atmosphere> atmospheres){
		
		Double divisor = 0.0;
		for(Atmosphere atmosphere : atmospheres){
			divisor += atmosphere.getPercentage();
		}
		for(Atmosphere atmosphere : atmospheres){
			atmosphere.setPercentage(atmosphere.getPercentage()/divisor);
		}
		return atmospheres;
	}
	
	private static List<Atmosphere> common(String operation, Double uvScalar,
			Double changeScalar, StarFactory starFactory) {
		List<Atmosphere> atmospheres = new ArrayList<Atmosphere>();
		List<StarToChemicalProfile> profileList = AtmosphereParts
				.starToAtmosphereList(starFactory);
		for (StarToChemicalProfile starToChemicalProfile : profileList) {
			Atmosphere atmosphere = new Atmosphere();
			if (operation.equals("LT")) {
				if (starToChemicalProfile.getUltraVioletReducingScale() < uvScalar) {
					Double newWeight = starToChemicalProfile
							.getWeightDuringAnalysis()
							* (100.0 + GenRandomRolls.Instance().getDraw(
									changeScalar));
					atmosphere.setPercentage(newWeight);
					atmosphere.setChem_name(starToChemicalProfile
							.getAtmosphereParts().name());
				} else {
					Double newWeight = starToChemicalProfile
							.getWeightDuringAnalysis()
							* GenRandomRolls.Instance().draw_rand()*(100.0);
					atmosphere.setPercentage(newWeight);
					atmosphere.setChem_name(starToChemicalProfile
							.getAtmosphereParts().name());
				}

			} else {
				if (starToChemicalProfile.getUltraVioletReducingScale() >= uvScalar) {
					Double newWeight = starToChemicalProfile
							.getWeightDuringAnalysis()
							* (100.0 + GenRandomRolls.Instance().getDraw(
									changeScalar));
					atmosphere.setPercentage(newWeight);
					atmosphere.setChem_name(starToChemicalProfile
							.getAtmosphereParts().name());
				} else {
					Double newWeight = starToChemicalProfile
							.getWeightDuringAnalysis()
							* GenRandomRolls.Instance().draw_rand()*(100.0);
					atmosphere.setPercentage(newWeight);
					atmosphere.setChem_name(starToChemicalProfile
							.getAtmosphereParts().name());
				}
			}

			atmospheres.add(atmosphere);
		}
		return normalize(atmospheres);
	}
	
	private static Map<String, AtmosphereResolutionIF> ruleMap = new HashMap<String, GenAtmosphere.AtmosphereResolutionIF>();
	static {
		// a cryogenic dwarf planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 5.0, starFactory);
			}
		});
		// ice dwarf planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 6.0, starFactory);
			}
		});
		// rocky dwarf palentoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 6.0, starFactory);
			}
		});
		// cythinan dwarf planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 10.0, starFactory);
			}
		});
		// molten dwarf planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 2.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("Cannot be ...");
			}
		});
		// cryogenic mini planetoid with Blue Super Giant 
		ruleMap.put("BLUE_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// rocky mini planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// cythian mini planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 12.0, starFactory);
			}
		});
		// molten mini planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("Cannot be ...");
			}
		});
		// cryogenic terran planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid with Blue Super Giant 
		ruleMap.put("BLUE_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 15.0, starFactory);
			}
		});
		// rocky terran planetoid witih Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 5.0, starFactory);
			}
		});
		// cythian terran planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 4.0, starFactory);
			}
		});
		// molten terran planetoid with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("Cannot be ...");
			}
		});
		// cryogenic super terran  giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 40.0, starFactory);
			}
		});
		// ice super terran with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 35.0, starFactory);
			}
		});
		//  super terran with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 20.0, starFactory);
			}
		});
		// super terran with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 10.0, starFactory);
			}
		});
		// molten super terran with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 10.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("This cannot be ...");
			}
		});
		// cryoggenic mini gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 5.0, starFactory);
			}
		});
		// ice mini gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 8.0, starFactory);
			}
		});
		// terran mini gas giant / super terran with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 10.0, starFactory);
			}
		});
		// mini gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 20.0, starFactory);
			}
		});
		// hot mini gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 25.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("Cannot be ...");
			}
		});
		// cryogenic gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});
		// ice gas giant with Blue Super Giant
		ruleMap.put("BLUE_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// super super terran or puffy gas giant
		ruleMap.put("BLUE_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 10.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("BLUE_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// very hot gas giant
		ruleMap.put("BLUE_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("this can't be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 10.0, starFactory);
			}
		});
		// ice suoer gas giant
		ruleMap.put("BLUE_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 10.0, starFactory);
			}
		});
		// puffy super gas giant 
		ruleMap.put("BLUE_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 10.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("BLUE_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});
		// super hot super gas giant
		ruleMap.put("BLUE_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 40.0, starFactory);
			}
		});
		
		ruleMap.put("BLUE_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("this cannot be ...");
			}
		});
		// cryogenic dwarf
		ruleMap.put("LTBL_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});
		// ice dwarf
		ruleMap.put("LTBL_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// rocky dwarf
		ruleMap.put("LTBL_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// cythian dwarf
		ruleMap.put("LTBL_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 11.0, starFactory);
			}
		});
		// molten dwarf
		ruleMap.put("LTBL_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 3.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("this cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});
		// molten mini planetoid 
		ruleMap.put("LTBL_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 17.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 15.0, starFactory);
			}
		});
		// rock terran plaentoid
		ruleMap.put("LTBL_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 7.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 22.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 42.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 38.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("LTBL_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 30.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 5.0, starFactory);
			}
		});
		// ice mini gas giant 
		ruleMap.put("LTBL_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 9.0, starFactory);
			}
		});
		// terran mini gas giant ; super terran planetoid
		ruleMap.put("LTBL_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 10.0, starFactory);
			}
		});
		// hot mini gas giant ; gas giant planetoid
		ruleMap.put("LTBL_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// utra hot mini gas giant; hot gas giant planetoid
		ruleMap.put("LTBL_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});
		
		ruleMap.put("LTBL_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException(" cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 30.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 20.0, starFactory);
			}
		});
		// gas poor puffy gas giant or supper super terran
		ruleMap.put("LTBL_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 5.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 20.0, starFactory);
			}
		});
		// very hot gas giant
		ruleMap.put("LTBL_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 35.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 5.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("LTBL_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// very hot super gas giant
		ruleMap.put("LTBL_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf
		ruleMap.put("WHIT_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 8.0, starFactory);
			}
		});
		// ice dwarf
		ruleMap.put("WHIT_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 10.0, starFactory);
			}
		});
		// rocky dwarf
		ruleMap.put("WHIT_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 10.0, starFactory);
			}
		});
		// cythian dwarf
		ruleMap.put("WHIT_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 11.0, starFactory);
			}
		});
		// molten dwarf
		ruleMap.put("WHIT_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 10.0, 4.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 16.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 15.0, 9.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 16.0, starFactory);
			}
		});
		// rock terran planetoid 
		ruleMap.put("WHIT_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 8.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 20.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 18.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("WHIT_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 50.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 17.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 20.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 6.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 11.0, starFactory);
			}
		});
		// gas poor mini gas giant ; super terran planetoid
		ruleMap.put("WHIT_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 2.0, starFactory);
			}
		});
		// hot mini gas
		ruleMap.put("WHIT_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 6.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 28.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or super super teran planetoid
		ruleMap.put("WHIT_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 4.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("WHIT_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 21.0, starFactory);
			}
		});
		// very hot gas giant
		ruleMap.put("WHIT_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 41.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super gas giant 
		ruleMap.put("WHIT_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// ice super gas giant 
		ruleMap.put("WHIT_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 11.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("WHIT_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 3.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("WHIT_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 11.0, starFactory);
			}
		});
		// rocky dwarf planetoid
		ruleMap.put("PYEL_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 11.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 3.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 10.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 10.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 18.0, starFactory);
			}
		});
		// rock terran planeotid
		ruleMap.put("PYEL_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 20.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 19.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("PYEL_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 58.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 18.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 18.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 7.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant ; super terran planetoid
		ruleMap.put("PYEL_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 3.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("PYEL_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 19.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 29.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 27.0, starFactory);
			}
		});
		// gas poor gas giant or super super terran planetoid
		ruleMap.put("PYEL_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 5.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 19.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 40.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant ; super gas giant
		ruleMap.put("PYEL_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 4.0, starFactory);
			}
		});
		// hot super gas giant; super gas giant
		ruleMap.put("PYEL_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});

		// cryogenic dwarf planetoid
		ruleMap.put("YELO_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 10.0, starFactory);
			}
		});
		// icw dwarf planetoid
		ruleMap.put("YELO_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 12.0, starFactory);
			}
		});
		// rocky dwarf planetoid
		ruleMap.put("YELO_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planet
		ruleMap.put("YELO_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});
		// molten dwarf planet 
		ruleMap.put("YELO_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 3.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid 
		ruleMap.put("YELO_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		//rock mini planetoid 
		ruleMap.put("YELO_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid 
		ruleMap.put("YELO_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid 
		ruleMap.put("YELO_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",  20.0, 4.0, starFactory);
			}
		});
		// molten terran planetoid 
		ruleMap.put("YELO_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 29.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super terran planetoid 
		ruleMap.put("YELO_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("YELO_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 60.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 20.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 29.0, starFactory);
			}
		});
		// gas poor gas giant ot super super terran planetoid
		ruleMap.put("YELO_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 6.0, starFactory);
			}
		});
		// hot gas giant or gas tiant
		ruleMap.put("YELO_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 5.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 38.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 4.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 6.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super planetoid
		ruleMap.put("ORNG_SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("ORNG_SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 61.0, starFactory);
			}
		});
		// cythian super terran planetoid 
		ruleMap.put("ORNG_SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 24.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 20.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 10.0, starFactory);
			}
		});
		// ice mini gas giant 
		ruleMap.put("ORNG_SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("ORNG_SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic gas giant 
		ruleMap.put("ORNG_SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 33.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("ORNG_SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 31.0, starFactory);
			}
		});
		// gas poor gas giant or super super terran planetoid
		ruleMap.put("ORNG_SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 25.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 42.0, starFactory);
			}
		});
		// ultra hot gas giant 
		ruleMap.put("ORNG_SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 40.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super gas giant 
		ruleMap.put("ORNG_SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or gas giant 
		ruleMap.put("ORNG_SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 7.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 36.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 30.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic dwarf planetoid 
		ruleMap.put("RED__SG_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__SG_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__SG_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__SG_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__SG_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 4.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__SG_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid 
		ruleMap.put("RED__SG_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__SG_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 10.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__SG_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 15.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__SG_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__SG_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 8.0, starFactory);
			}
		});
		// ice terran planetoid 
		ruleMap.put("RED__SG_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__SG_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 25.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__SG_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__SG_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super planetoid
		ruleMap.put("RED__SG_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ice super planetoid
		ruleMap.put("RED__SG_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("RED__SG_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__SG_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 25.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__SG_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 21.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__SG_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__SG_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__SG_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 23.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__SG_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__SG_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__SG_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 34.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("RED__SG_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 32.0, starFactory);
			}
		});
		// gas poor gas giant or super super terran planetoid
		ruleMap.put("RED__SG_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 26.0, starFactory);
			}
		});
		// hot gas giant or gas giant 
		ruleMap.put("RED__SG_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 43.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__SG_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 42.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__SG_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__SG_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__SG_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("RED__SG_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant 
		ruleMap.put("RED__SG_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 32.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("can't be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 5.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 6.0, starFactory);
			}
		});
		// rocky dwarf planetoid
		ruleMap.put("BLUE_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 6.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 10.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 2.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 12.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 15.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 5.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 4.0, starFactory);
			}
		});
		// molten terran planetoid 
		ruleMap.put("BLUE_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 40.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 35.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("BLUE_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 20.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 10.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 10.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 5.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 8.0, starFactory);
			}
		});
		// gas poor gas giant or super terran planetoid
		ruleMap.put("BLUE_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 10.0, starFactory);
			}
		});
		// mini gas giant or hot mini gas giant
		ruleMap.put("BLUE_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 20.0, starFactory);
			}
		});
		// very hot mini gas giant
		ruleMap.put("BLUE_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 25.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("BLUE_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// ultra terra or gas poor gas giant
		ruleMap.put("BLUE_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 10.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("BLUE_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException(" cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 10.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 10.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 10.0, starFactory);
			}
		});
		// not super gas giant 
		ruleMap.put("BLUE_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 40.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf
		ruleMap.put("LTBL_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});
		// ice dwarf
		ruleMap.put("LTBL_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// rocky dwarf
		ruleMap.put("LTBL_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// cythian dwarf
		ruleMap.put("LTBL_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 11.0, starFactory);
			}
		});
		// molten dwarf
		ruleMap.put("LTBL_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 3.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid 
		ruleMap.put("LTBL_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 15.0, starFactory);
			}
		});
		// rock terran planetoid 
		ruleMap.put("LTBL_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 7.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 22.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 42.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 38.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 30.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});
	
		ruleMap.put("LTBL_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 5.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 9.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("LTBL_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 10.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 30.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 20.0, starFactory);
			}
		});
		// gas poor puffy gas giant or ultra terran planetoid
		ruleMap.put("LTBL_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 5.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 20.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("LTBL_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 35.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");	
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 5.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException(" cannot be ...");
			}
		});
		// cryogenic dwarf
		ruleMap.put("WHIT_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 8.0, starFactory);
			}
		});
		// ice dwarf 
		ruleMap.put("WHIT_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 10.0, starFactory);
			}
		});
		// rocky dwarf
		ruleMap.put("WHIT_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 10.0, starFactory);
			}
		});
		// cythian dwarf
		ruleMap.put("WHIT_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 11.0, starFactory);
			}
		});
		// molten dwarf
		ruleMap.put("WHIT_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 10.0, 4.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 12.0, starFactory);
			}
		});
		// rocky mini planeotid
		ruleMap.put("WHIT_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 16.0, starFactory);
			}
		});
		// moltem mini planetoid
		ruleMap.put("WHIT_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 15.0, 9.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 16.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 8.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 18.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("WHIT_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 50.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 17.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 20.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 6.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 11.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("WHIT_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 2.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("WHIT_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 6.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 2.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("WHIT_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetopid
		ruleMap.put("WHIT_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 4.0, starFactory);
			}
		});
		// hot gas giant 
		ruleMap.put("WHIT_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 21.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 41.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 11.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("WHIT_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 3.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("WHIT_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 42.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});

		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 11.0, starFactory);
			}
		});
		// rocky dwarf planetoid
		ruleMap.put("PYEL_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 11.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 3.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 8.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 10.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 10.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 18.0, starFactory);
			}
		});
		// rocky terran planetoid
		ruleMap.put("PYEL_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 20.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 19.0, starFactory);
			}
		});
		// rocky super terran planetoid
		ruleMap.put("PYEL_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 58.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 18.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 18.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 7.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 3.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("PYEL_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 19.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 29.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 27.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("PYEL_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 5.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 19.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 13.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giants
		ruleMap.put("PYEL_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant or gas giant
		ruleMap.put("PYEL_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 4.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});
		
		ruleMap.put("PYEL_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 12.0, starFactory);
			}
		});
		// rocky dwarf planetoid
		ruleMap.put("YELO_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 3.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 4.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 29.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// rocky super terran planetoide
		ruleMap.put("YELO_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 60.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 20.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giantot super terran planetoid
		ruleMap.put("YELO_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 29.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("YELO_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 6.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 5.0, starFactory);
			}
		});
		// hot super gas giant or gas giant
		ruleMap.put("YELO_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 38.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("YELO_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});

		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 4.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 6.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 10.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 61.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 24.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 20.0, starFactory);
			}
		});
		
		ruleMap.put("ORNG_SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 10.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("ORNG_SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 33.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 31.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("ORNG_SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 25.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 40.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic super gas giant
		ruleMap.put("ORNG_SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 7.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 36.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 30.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__SG_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__SG_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__SG_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__SG_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__SG_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 4.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__SG_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__SG_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__SG_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 10.0, starFactory);
			}
		});
		// cyethian mini planetoid
		ruleMap.put("RED__SG_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 15.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__SG_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__SG_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 8.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__SG_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__SG_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 25.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__SG_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__SG_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__SG_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__SG_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__SG_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__SG_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 25.0, starFactory);
			}
		});
		// molten super terran planetoid 
		ruleMap.put("RED__SG_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 21.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__SG_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant 
		ruleMap.put("RED__SG_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__SG_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 23.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("RED__SG_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__SG_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__SG_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 34.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("RED__SG_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 32.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("RED__SG_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 26.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__SG_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 43.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__SG_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 42.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__SG_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__SG_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__SG_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot super gas giant or gas giant
		ruleMap.put("RED__SG_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__SG_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 32.0, starFactory);
			}
		});

		ruleMap.put("RED__SG_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 8.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BLUE_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 11.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 50.0, 3.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 11.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 25.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 16.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 17.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 7.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 21.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 42.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 36.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 22.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 12.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 6.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 10.0, starFactory);
			}
		});
		// gas poor mini gas giant or gas giant
		ruleMap.put("BLUE_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("BLUE_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant 
		ruleMap.put("BLUE_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 26.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic gas giant
		ruleMap.put("BLUE_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("BLUE_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// ultra terran planetoid or gas poor gas giant
		ruleMap.put("BLUE_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 12.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BLUE_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});
		// hot super gas giant or gas giant
		ruleMap.put("BLUE_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 42.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("LTBL_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 8.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("LTBL_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("LTBL_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("LTBL_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("LTBL_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 5.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 16.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 18.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 15.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 8.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 44.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 40.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 32.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("LTBL_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 21.0, starFactory);
			}
		});
		// molten super planetoid
		ruleMap.put("LTBL_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 11.0, starFactory);
			}
		});
		// gas poor mini gas giant or gas giant
		ruleMap.put("LTBL_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("LTBL_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 9.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 22.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("LTBL_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 7.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("LTBL_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 22.0, starFactory);
			}
		});
		// ultra hot gass giants
		ruleMap.put("LTBL_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("LTBL_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 7.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("WHIT_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 12.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 12.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 6.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 15.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 18.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 10.0, starFactory);
			}
		});
		// cyuthian terran planetoid
		ruleMap.put("WHIT_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 20.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("WHIT_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 50.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 19.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 20.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("WHIT_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 4.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 8.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 28.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 6.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant 
		ruleMap.put("WHIT_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 43.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("WHIT_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 5.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("WHIT_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 13.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 5.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 12.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PYEL_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 60.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 20.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryigenic mini gas giant
		ruleMap.put("PYEL_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 9.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 14.0, starFactory);
			}
		});
		// gas poort mini gas giant or super terran planetoid
		ruleMap.put("PYEL_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 5.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("PYEL_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 21.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 29.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("PYEL_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 7.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 26.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic suepr gas giant
		ruleMap.put("PYEL_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 6.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 12.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 14.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("YELO_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 14.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 5.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid 
		ruleMap.put("YELO_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 22.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 11.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 21.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 16.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 6.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 31.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 22.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 10.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 22.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 33.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 31.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("YELO_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 8.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 7.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 48.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 15.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// molten dwarg planetid
		ruleMap.put("ORNG_GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 6.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 16.0, starFactory);
			}
		});
		// molten mini plantoid
		ruleMap.put("ORNG_GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 21.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 18.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 26.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 22.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("ORNG_GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 35.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 33.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 27.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 44.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 42.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 15.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 15.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 38.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 32.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__GI_II" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__GI_II" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__GI_II" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 16.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__GI_II" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__GI_II" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 6.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__GI_II" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 17.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__GI_II" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 18.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__GI_II" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__GI_II" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__GI_II" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__GI_II" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__GI_II" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__GI_II" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 27.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__GI_II" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__GI_II" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic super terran planetoid
		ruleMap.put("RED__GI_II" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 26.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__GI_II" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__GI_II" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__GI_II" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 27.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__GI_II" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 21.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic mini gas giant
		ruleMap.put("RED__GI_II" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 14.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__GI_II" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 15.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__GI_II" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 25.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__GI_II" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 26.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__GI_II" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 27.0, starFactory);
			}
		});
		
		ruleMap.put("RED__GI_II" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant 
		ruleMap.put("RED__GI_II" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 36.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("RED__GI_II" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 34.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__GI_II" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 28.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__GI_II" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 45.0, starFactory);
			}
		});
		// ultra hot gs giant
		ruleMap.put("RED__GI_II" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 44.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__GI_II" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__GI_II" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 16.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__GI_II" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 10.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("RED__GI_II" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 36.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__GI_II" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 34.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_II" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 7.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 8.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BLUE_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 11.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 50.0, 3.0, starFactory);
			}
		});
		
		ruleMap.put("BLUE_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 11.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 16.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 11.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 17.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 7.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 5.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 21.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic super terran planetoid
		ruleMap.put("BLUE_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 42.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 36.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 22.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 12.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 6.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 10.0, starFactory);
			}
		});
		// gas poor mini gas giant or mini gas giant
		ruleMap.put("BLUE_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BLUE_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("BLUE_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 26.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("BLUE_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BLUE_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 12.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BLUE_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 22.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 12.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 12.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BLUE_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 42.0, starFactory);
			}
		});

		ruleMap.put("BLUE_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});

		// cryogenic dwarf planetoid

		ruleMap.put("LTBL_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 8.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("LTBL_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("LTBL_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid 
		ruleMap.put("LTBL_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("LTBL_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 5.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 16.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 18.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 15.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 8.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",50.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 44.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 40.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 32.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("LTBL_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 21.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 11.0, starFactory);
			}
		});
		// gas poor mini gas giant or mini gas giant
		ruleMap.put("LTBL_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 9.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 22.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("LTBL_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 7.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("LTBL_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 22.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("LTBL_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 14.0, starFactory);
			}
		});
		//ice super gas giant
		ruleMap.put("LTBL_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("LTBL_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 7.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});

		ruleMap.put("LTBL_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoif
		ruleMap.put("WHIT_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 12.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 12.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 6.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 18.0, starFactory);
			}
		});
		// rock terran planeotid
		ruleMap.put("WHIT_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 7.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 20.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("WHIT_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 50.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 19.0, starFactory);
			}
		});
		// molten super terran plantoid
		ruleMap.put("WHIT_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 20.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("WHIT_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 4.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 8.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 28.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 6.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 43.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or gas giant
		ruleMap.put("WHIT_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 5.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("WHIT_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("WHIT_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 13.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 5.0, starFactory);
			}
		});
		
		ruleMap.put("PYEL_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 12.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 12.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 30.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PYEL_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 60.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 20.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 9.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 5.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("PYEL_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 21.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 29.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("PYEL_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 7.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 21.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 26.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 6.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 46.0, starFactory);
			}
		});

		ruleMap.put("PYEL_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 12.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 14.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("YELO_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 14.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 5.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 22.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 11.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 21.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 16.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 6.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 31.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});
		// ice suoer terran planetoid
		ruleMap.put("YELO_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 22.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 10.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 22.0, starFactory);
			}
		});
		// hot mini gas giant 
		ruleMap.put("YELO_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 33.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("YELO_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 31.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super planetoid
		ruleMap.put("YELO_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 8.0, starFactory);
			}
		});
		// hot gas giant ore gas giant
		ruleMap.put("YELO_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 44.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 7.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 40.0, starFactory);
			}
		});
		// ultra how super gas giant
		ruleMap.put("YELO_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 48.0, starFactory);
			}
		});

		ruleMap.put("YELO_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 15.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 6.0, starFactory);
			}
		});
		
		ruleMap.put("ORNG_GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 16.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 21.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 18.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 12.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 26.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 22.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 1.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant 
		ruleMap.put("ORNG_GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 24.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("ORNG_GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant 
		ruleMap.put("ORNG_GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 35.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("ORNG_GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 33.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 27.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 44.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 42.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 15.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 15.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 38.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 32.0, starFactory);
			}
		});

		ruleMap.put("ORNG_GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__GI_I" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__GI_I" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__GI_I" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 16.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__GI_I" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__GI_I" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 6.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__GI_I" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 17.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__GI_I" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 18.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__GI_I" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__GI_I" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__GI_I" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 12.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic terran planetoid
		ruleMap.put("RED__GI_I" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 10.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__GI_I" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__GI_I" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 27.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__GI_I" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid 
		ruleMap.put("RED__GI_I" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__GI_I" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 26.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__GI_I" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 23.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__GI_I" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 26.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__GI_I" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 27.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__GI_I" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 21.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__GI_I" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 13.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__GI_I" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 15.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__GI_I" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 15.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__GI_I" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 26.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__GI_I" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 27.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__GI_I" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 36.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("RED__GI_I" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 34.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__GI_I" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 28.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__GI_I" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 45.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__GI_I" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 44.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__GI_I" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__GI_I" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 16.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__GI_I" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 10.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("RED__GI_I" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 36.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__GI_I" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 34.0, starFactory);
			}
		});

		ruleMap.put("RED__GI_I" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});

		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 8.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 9.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BLUE_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 10.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 12.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 48.0, 4.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 39.0, 16.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 18.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 19.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 44.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 57.0, 38.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 24.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 47.0, 14.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 14.0, starFactory);
			}
		});
		
		ruleMap.put("BLUE_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 7.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or mini gas giant
		ruleMap.put("BLUE_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 62.0, 14.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BLUE_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("BLUE_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 28.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("BLUE_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 24.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BLUE_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BLUE_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 24.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 24.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 13.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BLUE_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 33.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 44.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf
		ruleMap.put("LTBL_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice dwarf
		ruleMap.put("LTBL_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 11.0, starFactory);
			}
		});
		// rocky dwarf
		ruleMap.put("LTBL_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 11.0, starFactory);
			}
		});
		// cythian dwarf
		ruleMap.put("LTBL_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 13.0, starFactory);
			}
		});
		// molten dwarf
		ruleMap.put("LTBL_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 5.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 9.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 17.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 9.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 7.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cyrogenic super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 44.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 40.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 32.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 17.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant 
		ruleMap.put("LTBL_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 7.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 11.0, starFactory);
			}
		});
		// gas poor mini gas giatn or super terran planetoid
		ruleMap.put("LTBL_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 62.0, 12.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 27.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 9.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 21.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("LTBL_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 7.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 21.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 12.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 7.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 33.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 41.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 12.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("WHIT_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 14.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 7.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 11.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 15.0, starFactory);
			}
		});
		// rock mini plantoid
		ruleMap.put("WHIT_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 18.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 19.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 13.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 17.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 47.0, 12.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 24.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 22.0, starFactory);
			}
		});
		// rock super terran plantoid 
		ruleMap.put("WHIT_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 52.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 20.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 22.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 10.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 15.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("WHIT_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 6.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 29.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("WHIT_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 27.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 24.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cyrogenic super gas giant
		ruleMap.put("WHIT_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 15.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 13.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("WHIT_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 7.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("WHIT_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 44.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 6.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 15.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 7.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 13.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 22.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 14.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 17.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 22.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 32.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 24.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 26.0, starFactory);
			}
		});
		//rock super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 20.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 11.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 18.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 6.0, 20.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("PYEL_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 22.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 23.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 31.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("PYEL_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 8.0, 9.0, starFactory);
			}
		});
		// hot gas giant or gsa giant
		ruleMap.put("PYEL_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant 
		ruleMap.put("PYEL_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 27.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 16.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 44.0, starFactory);
			}
		});
		// ultra how super gas giant
		ruleMap.put("PYEL_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 48.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 14.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 14.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("YELO_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 16.0, starFactory);
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 7.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 18.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 24.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 12.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 18.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 18.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 33.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 26.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 28.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 62.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 24.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 24.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 18.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 22.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 26.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 35.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 33.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super planetoid
		ruleMap.put("YELO_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 44.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 16.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 9.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 49.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 15.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 17.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 17.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 17.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 8.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 13.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 16.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 13.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 22.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 19.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 14.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 27.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 64.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 28.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 24.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 14.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 26.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("ORNG_SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 26.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 27.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 37.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 35.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 30.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 43.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 19.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 17.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 11.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 40.0, starFactory);
			}
		});
		// ultra hot suer gas giant
		ruleMap.put("ORNG_SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__SUBGI" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__SUBGI" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 17.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__SUBGI" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 18.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__SUBGI" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 18.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__SUBGI" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 8.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__SUBGI" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 19.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__SUBGI" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 20.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__SUBGI" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 14.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__SUBGI" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 19.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__SUBGI" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 14.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__SUBGI" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__SUBGI" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__SUBGI" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 29.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__SUBGI" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__SUBGI" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 12.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__SUBGI" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 28.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__SUBGI" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__SUBGI" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 26.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__SUBGI" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 29.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__SUBGI" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 22.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__SUBGI" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__SUBGI" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 15.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__SUBGI" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 17.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__SUBGI" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__SUBGI" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 29.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gs giant
		ruleMap.put("RED__SUBGI" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 38.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("RED__SUBGI" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 36.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__SUBGI" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 30.0, starFactory);
			}
		});
		// hot gas giant or gas giant  
		ruleMap.put("RED__SUBGI" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 46.0, starFactory);
			}
		});
		// ultra hot gas giant 
		ruleMap.put("RED__SUBGI" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 44.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant 
		ruleMap.put("RED__SUBGI" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 17.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__SUBGI" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 17.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__SUBGI" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 13.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("RED__SUBGI" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 38.0, starFactory);
			}
		});
		// ultra hot suoer gas giant 
		ruleMap.put("RED__SUBGI" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 36.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBGI" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 9.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 10.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BLUE_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 5.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 13.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 39.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 19.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 11.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 45.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 57.0, 39.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 25.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 47.0, 15.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 15.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 13.0, starFactory);
			}
		});
		// rock mini gas giant
		ruleMap.put("BLUE_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 62.0, 15.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BLUE_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 25.0, starFactory);
			}
		});
		// ultar hot mini gas giant
		ruleMap.put("BLUE_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 29.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 35.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("BLUE_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BLUE_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 15.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BLUE_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 3.0, 25.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 14.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BLUE_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 33.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 44.0, starFactory);
			}
		});

		ruleMap.put("BLUE_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dearf planetoid
		ruleMap.put("LTBL_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("LTBL_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 12.0, starFactory);
			}
		});
		// rocky dwarf palentoid
		ruleMap.put("LTBL_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("LTBL_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("LTBL_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 52.0, 6.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 17.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 17.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 10.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 18.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 50.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 25.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 60.0, 45.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 41.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 33.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 18.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("LTBL_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 62.0, 13.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 28.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 11.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 21.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("LTBL_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 22.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("LTBL_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 8.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 42.0, starFactory);
			}
		});

		ruleMap.put("LTBL_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("WHIT_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 14.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 8.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 19.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 14.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 16.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 18.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 47.0, 13.0, starFactory);
			}
		});
		// cythian terran planetoid 
		ruleMap.put("WHIT_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 54.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 24.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("WHIT_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 2.0, 53.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 23.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 11.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("WHIT_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 7.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 23.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 30.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 28.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 12.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("WHIT_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 8.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("WHIT_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 45.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 7.0, starFactory);
			}
		});

		ruleMap.put("WHIT_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 14.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 16.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 16.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 8.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 14.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 15.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 17.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 23.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 15.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 18.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 15.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 33.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 25.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PYEL_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 21.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 19.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 6.0, 21.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("PYEL_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 23.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 12.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 33.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 32.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("PYEL_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 8.0, 10.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 24.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 49.0, starFactory);
			}
		});

		ruleMap.put("PYEL_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 15.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("YELO_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 17.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 17.0, starFactory);
			}
		});
		// molten dwarf plnatoid
		ruleMap.put("YELO_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 7.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 15.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 19.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 24.0, starFactory);
			}
		});
		// molten mini plantoid
		ruleMap.put("YELO_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 13.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 19.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 24.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 19.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 34.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 27.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 29.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 25.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 25.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 13.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 19.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 23.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 27.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 37.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 35.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super planetoid
		ruleMap.put("YELO_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 13.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 26.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 45.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 17.0, starFactory);
			}
		});
		// ice super giant
		ruleMap.put("YELO_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 17.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 10.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 43.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 50.0, starFactory);
			}
		});

		ruleMap.put("YELO_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 16.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 18.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 18.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 18.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 9.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 14.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 17.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 16.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 14.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 13.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 20.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 11.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 15.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 28.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 65.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 29.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 23.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 15.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 27.0, starFactory);
			}
		});
		// hot mini gas giant or gas giant
		ruleMap.put("ORNG_MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 27.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 28.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 38.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 36.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 31.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 46.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 44.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 20.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 12.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 41.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});

		ruleMap.put("ORNG_MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});  
		// cryogenic dwarf planetoid
		ruleMap.put("RED__MAINS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 16.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__MAINS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 18.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__MAINS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 19.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__MAINS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 19.0, starFactory);
			}
		});
		// molten dwarf plaanetoid
		ruleMap.put("RED__MAINS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__MAINS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 20.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__MAINS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 21.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__MAINS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__MAINS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__MAINS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 15.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		/// cryogenic terran planetoid
		ruleMap.put("RED__MAINS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__MAINS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 24.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__MAINS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 30.0, starFactory);
			}
		});
		// cythian terran planetoid room 
		ruleMap.put("RED__MAINS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__MAINS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 13.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__MAINS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 29.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__MAINS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__MAINS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 27.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__MAINS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 30.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__MAINS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 23.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__MAINS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__MAINS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 16.0, starFactory);
			}
		});
		// gsa poor mini gas giant or super terran planetoid
		ruleMap.put("RED__MAINS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 18.0, starFactory);
			}
		});
		// hot mini gas giant 
		ruleMap.put("RED__MAINS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 30.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__MAINS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 31.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__MAINS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 39.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("RED__MAINS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__MAINS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 31.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__MAINS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 48.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__MAINS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__MAINS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 19.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__MAINS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 19.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__MAINS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 15.0, starFactory);
			}
		});
		// hot super gas giant 
		ruleMap.put("RED__MAINS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 39.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__MAINS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 37.0, starFactory);
			}
		});

		ruleMap.put("RED__MAINS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",31.0, 9.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 10.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BLUE_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 11.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 38.0, 13.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 49.0, 5.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 9.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 13.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 40.0, 17.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 46.0, 19.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 24.0, 20.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 11.0, starFactory);
			}
		});
		// cythian teerran planetoid
		ruleMap.put("BLUE_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 46.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 61.0, 45.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 58.0, 39.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 53.0, 25.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 15.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 15.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("BLUE_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 15.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BLUE_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("BLUE_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 29.0, starFactory);
			}
		});
		
		ruleMap.put("BLUE_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 35.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("BLUE_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 25.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BLUE_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 15.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BLUE_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 25.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 14.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BLUE_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 33.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 44.0, starFactory);
			}
		});

		ruleMap.put("BLUE_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("LTBL_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("LTBL_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 12.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("LTBL_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("LTBL_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("LTBL_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 53.0, 6.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 17.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 17.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("LTBL_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 10.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 14.0, starFactory);
			}
		});
		// ice traan planetoid
		ruleMap.put("LTBL_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 18.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 51.0, 10.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 56.0, 8.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 25.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// crygenic super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 61.0, 45.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 59.0, 41.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 56.0, 33.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 46.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 18.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 8.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 12.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("LTBL_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 63.0, 13.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 28.0, starFactory);
			}
		});
		// ultra not mini gas giant
		ruleMap.put("LTBL_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 11.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 32.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("LTBL_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 21.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("LTBL_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 8.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 22.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("LTBL_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 37.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});
		// ultra super gas giant
		ruleMap.put("LTBL_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 8.0, starFactory);
			}
		});

		ruleMap.put("LTBL_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("WHIT_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 8.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 15.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 8.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 19.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 14.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 16.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 18.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 13.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 10.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 22.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 24.0, starFactory);
			}
		});
		//rock super terran planetoid
		ruleMap.put("WHIT_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 53.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 49.0, 22.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 23.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 11.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant 
		ruleMap.put("WHIT_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 7.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 23.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 30.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 28.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 9.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 12.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// gas poor super gas giant or super gsa giant
		ruleMap.put("WHIT_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 8.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("WHIT_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 45.0, starFactory);
			}
		});
		// ultra hot super gas giant 
		ruleMap.put("WHIT_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 7.0, starFactory);
			}
		});

		ruleMap.put("WHIT_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 16.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 16.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 16.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 8.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 14.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PYEL_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 15.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 17.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 23.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 15.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 18.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 15.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 33.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 25.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 6.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 21.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 19.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 7.0, 21.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("PYEL_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 23.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 12.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 33.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PYEL_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 33.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("PYEL_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 9.0, 10.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 24.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 16.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 9.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 45.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 49.0, starFactory);
			}
		});

		ruleMap.put("PYEL_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 15.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("YELO_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// rock dwarf planetoide
		ruleMap.put("YELO_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 17.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 17.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 7.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 15.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 19.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 24.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 13.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 19.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 24.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 19.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 34.0, starFactory);
			}
		});
		
		ruleMap.put("YELO_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 27.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 29.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 63.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 39.0, 25.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 25.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 13.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 19.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 23.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 25.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 27.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 37.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 35.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("YELO_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 13.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 17.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 17.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 10.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 44.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 50.0, starFactory);
			}
		});

		ruleMap.put("YELO_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 16.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 18.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 18.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 18.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 9.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 14.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("ORNG_SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 17.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 14.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 13.0, starFactory);
			}
		});
		// ice terran planeotid
		ruleMap.put("ORNG_SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 23.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 20.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 11.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 15.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 28.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 65.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 29.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 28.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 15.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 27.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("ORNG_SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 27.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 38.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 36.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 31.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 46.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 45.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 20.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 12.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 41.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 34.0, starFactory);
			}
		});

		ruleMap.put("ORNG_SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__SUBDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 16.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__SUBDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 18.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__SUBDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 19.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__SUBDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 19.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__SUBDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 9.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__SUBDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 16.0, 20.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__SUBDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 21.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__SUBDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 15.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__SUBDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 20.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__SUBDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 15.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__SUBDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__SUBDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 24.0, starFactory);
			}
		});
		// rock terran planeotid
		ruleMap.put("RED__SUBDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 30.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__SUBDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__SUBDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 13.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__SUBDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 29.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__SUBDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 27.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__SUBDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 27.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__SUBDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 30.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__SUBDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 23.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__SUBDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__SUBDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__SUBDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 18.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__SUBDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 30.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__SUBDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 31.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__SUBDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 39.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("RED__SUBDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 37.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__SUBDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 31.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__SUBDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 48.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__SUBDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 45.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__SUBDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 19.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__SUBDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 19.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__SUBDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 15.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("RED__SUBDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 39.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__SUBDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 37.0, starFactory);
			}
		});

		ruleMap.put("RED__SUBDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BLUE_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",31.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BLUE_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",36.0, 11.0, starFactory);
			}
		});
		// rock dwarf planeoid
		ruleMap.put("BLUE_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",36.0, 12.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BLUE_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",38.0, 14.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BLUE_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",49.0, 6.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BLUE_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",37.0, 10.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BLUE_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",32.0, 14.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BLUE_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",30.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BLUE_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",40.0, 18.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BLUE_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",46.0, 20.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BLUE_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",22.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BLUE_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",24.0, 21.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BLUE_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",36.0, 12.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BLUE_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",46.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BLUE_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",6.0, 21.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("BLUE_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",61.0, 46.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BLUE_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",58.0, 40.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BLUE_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",53.0, 26.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BLUE_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",48.0, 16.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BLUE_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",41.0, 16.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BLUE_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",5.0, 9.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("BLUE_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",7.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("BLUE_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">",3.0, 16.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BLUE_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",10.0, 26.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("BLUE_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",7.0, 30.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BLUE_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",7.0, 36.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("BLUE_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",8.0, 26.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BLUE_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",26.0, 16.0, starFactory);
			}
		});
		// hot gas giant or gas giant 
		ruleMap.put("BLUE_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 26.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BLUE_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 26.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BLUE_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 15.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BLUE_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 15.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("BLUE_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 15.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BLUE_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BLUE_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});

		ruleMap.put("BLUE_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("LTBL_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 11.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("LTBL_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 13.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("LTBL_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 13.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("LTBL_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 15.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("LTBL_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 53.0, 7.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("LTBL_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 11.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("LTBL_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 18.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("LTBL_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 18.0, starFactory);
			}
		});
		// cythian mini panetoid
		ruleMap.put("LTBL_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 19.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("LTBL_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 11.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("LTBL_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 15.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("LTBL_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("LTBL_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 51.0, 11.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("LTBL_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 56.0, 9.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("LTBL_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("LTBL_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 61.0, 46.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("LTBL_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 59.0, 42.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("LTBL_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 56.0, 34.0, starFactory);
			}
		});
		// hot super terran planetoid
		ruleMap.put("LTBL_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 46.0, 24.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("LTBL_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 19.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("LTBL_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 9.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("LTBL_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 13.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("LTBL_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 63.0, 14.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("LTBL_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 29.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("LTBL_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 12.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("LTBL_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 33.0, starFactory);
			}
		});
		// ice gas giante
		ruleMap.put("LTBL_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 21.0, starFactory);
			}
		});
		// gas poor gas giants or uletra terran planetoid
		ruleMap.put("LTBL_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 9.0, starFactory);
			}
		});
		// hot gas giant
		ruleMap.put("LTBL_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 23.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("LTBL_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 38.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("LTBL_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 14.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("LTBL_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 15.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("LTBL_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("LTBL_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 15.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("LTBL_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 9.0, starFactory);
			}
		});

		ruleMap.put("LTBL_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("WHIT_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 14.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("WHIT_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 9.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("WHIT_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 16.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("WHIT_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 17.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("WHIT_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 12.0, 9.0, starFactory);
			}
		});
		
		ruleMap.put("WHIT_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("WHIT_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 13.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("WHIT_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 17.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("WHIT_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 36.0, 20.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("WHIT_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<",42.0, 21.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("WHIT_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 15.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("WHIT_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 17.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("WHIT_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 19.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("WHIT_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 48.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("WHIT_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 55.0, 11.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("WHIT_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 11.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("WHIT_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 23.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("WHIT_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 25.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("WHIT_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 54.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("WHIT_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 49.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("WHIT_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 44.0, 24.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("WHIT_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 12.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("WHIT_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 17.0, starFactory);
			}
		});
		// gas poor mini gas giant or or ultra terran planetoid
		ruleMap.put("WHIT_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 8.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("WHIT_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("WHIT_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 12.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("WHIT_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 31.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("WHIT_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 29.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("WHIT_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 10.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("WHIT_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 26.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("WHIT_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("WHIT_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 17.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("WHIT_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 15.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("WHIT_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 9.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("WHIT_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 46.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("WHIT_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 8.0, starFactory);
			}
		});

		ruleMap.put("WHIT_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PYEL_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 15.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PYEL_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 17.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PYEL_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 17.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PYEL_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 17.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PYEL_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 9.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PYEL_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid 
		ruleMap.put("PYEL_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 32.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PYEL_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 18.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PYEL_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 24.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PYEL_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 16.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PYEL_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 19.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PYEL_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 24.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PYEL_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 16.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PYEL_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 11.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PYEL_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 34.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PYEL_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 26.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PYEL_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 6.0, 28.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PYEL_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 64.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PYEL_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 42.0, 23.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PYEL_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 22.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ....");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PYEL_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 13.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PYEL_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 20.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PYEL_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 7.0, 22.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("PYEL_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 24.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PYEL_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 27.0, 13.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PYEL_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 34.0, starFactory);
			}
		});
		// ice gas giant 
		ruleMap.put("PYEL_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 8.0, 34.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("PYEL_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 9.0, 11.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PYEL_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 25.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PYEL_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 29.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PYEL_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 17.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PYEL_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 19.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("PYEL_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 10.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PYEL_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 46.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PYEL_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 50.0, starFactory);
			}
		});

		ruleMap.put("PYEL_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("YELO_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 16.0, starFactory);
			}
		});
		// ice dwarf palentoid
		ruleMap.put("YELO_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 35.0, 16.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("YELO_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 18.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("YELO_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 43.0, 18.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("YELO_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 8.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("YELO_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 29.0, 16.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("YELO_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 33.0, 16.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("YELO_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 41.0, 20.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("YELO_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 45.0, 25.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("YELO_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 14.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("YELO_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 13.0, 20.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("YELO_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 15.0, 25.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("YELO_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 20.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("YELO_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 10.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("YELO_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 35.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("YELO_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 28.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("YELO_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 30.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("YELO_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 64.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("YELO_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 39.0, 26.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("YELO_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 26.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("YELO_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 4.0, 14.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("YELO_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 20.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("YELO_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 24.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("YELO_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("YELO_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 26.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("YELO_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 38.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("YELO_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 36.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("YELO_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 14.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("YELO_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 27.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("YELO_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 46.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("YELO_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 17.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("YELO_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("YELO_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 11.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("YELO_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 45.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("YELO_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 51.0, starFactory);
			}
		});

		ruleMap.put("YELO_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("ORNG_DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 17.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("ORNG_DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 19.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("ORNG_DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 19.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("ORNG_DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 37.0, 19.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("ORNG_DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 10.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("ORNG_DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 15.0, starFactory);
			}
		});
		// ice mini planetoid 
		ruleMap.put("ORNG_DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 18.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("ORNG_DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 17.0, 17.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("ORNG_DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 31.0, 19.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("ORNG_DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 15.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("ORNG_DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 19.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("ORNG_DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 24.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("ORNG_DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 21.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("ORNG_DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 12.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("ORNG_DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 16.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("ORNG_DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 29.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("ORNG_DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 66.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("ORNG_DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 30.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("ORNG_DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 29.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 10.0, 29.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("ORNG_DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("ORNG_DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 14.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("ORNG_DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 28.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("ORNG_DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 28.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("ORNG_DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 29.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("ORNG_DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 39.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("ORNG_DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 37.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("ORNG_DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 32.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("ORNG_DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 47.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("ORNG_DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 46.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("ORNG_DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 21.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("ORNG_DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 19.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("ORNG_DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 13.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("ORNG_DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 42.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("ORNG_DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 35.0, starFactory);
			}
		});

		ruleMap.put("ORNG_DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("RED__DWARF" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 17.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("RED__DWARF" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 19.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("RED__DWARF" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 28.0, 20.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("RED__DWARF" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 34.0, 20.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("RED__DWARF" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 10.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("RED__DWARF" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 16.0, 21.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("RED__DWARF" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 18.0, 22.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("RED__DWARF" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 16.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("RED__DWARF" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 21.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("RED__DWARF" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 16.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("RED__DWARF" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 16.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("RED__DWARF" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 25.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("RED__DWARF" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 21.0, 31.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("RED__DWARF" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 23.0, 11.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("RED__DWARF" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 20.0, 14.0, starFactory);
			}
		});
		
		ruleMap.put("RED__DWARF" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("RED__DWARF" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 30.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("RED__DWARF" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 28.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("RED__DWARF" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 11.0, 27.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("RED__DWARF" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 22.0, 31.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("RED__DWARF" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 25.0, 24.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("RED__DWARF" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 14.0, 17.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("RED__DWARF" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 17.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("RED__DWARF" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 9.0, 19.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("RED__DWARF" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 31.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("RED__DWARF" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 32.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("RED__DWARF" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 40.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("RED__DWARF" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 6.0, 38.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("RED__DWARF" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 32.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("RED__DWARF" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 49.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("RED__DWARF" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 46.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("RED__DWARF" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 30.0, 20.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("RED__DWARF" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 26.0, 20.0, starFactory);
			}
		});
		// gas poor super gas giant or super gas giant
		ruleMap.put("RED__DWARF" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// hot super gas giant
		ruleMap.put("RED__DWARF" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 39.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("RED__DWARF" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 7.0, 37.0, starFactory);
			}
		});

		ruleMap.put("RED__DWARF" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("PURPLE_RED" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("PURPLE_RED" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 10.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("PURPLE_RED" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 10.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("PURPLE_RED" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 10.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("PURPLE_RED" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common("<", 5.0, 10.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("PURPLE_RED" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("PURPLE_RED" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("PURPLE_RED" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("PURPLE_RED" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("PURPLE_RED" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 12.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("PURPLE_RED" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("PURPLE_RED" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 14.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("PURPLE_RED" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("PURPLE_RED" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 14.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("PURPLE_RED" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 14.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("PURPLE_RED" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 16.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("PURPLE_RED" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 16.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("PURPLE_RED" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("PURPLE_RED" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 16.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("PURPLE_RED" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 16.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("PURPLE_RED" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("PURPLE_RED" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("PURPLE_RED" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// hot mini gas giant
		ruleMap.put("PURPLE_RED" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("PURPLE_RED" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 18.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("PURPLE_RED" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 17.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("PURPLE_RED" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 17.0, starFactory);
			}
		});
		// gas poor gas giant or ultra super terran planetoid
		ruleMap.put("PURPLE_RED" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 17.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("PURPLE_RED" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 19.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("PURPLE_RED" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 19.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("PURPLE_RED" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 18.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("PURPLE_RED" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 18.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("PURPLE_RED" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 18.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("PURPLE_RED" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 20.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("PURPLE_RED" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 19.0, starFactory);
			}
		});

		ruleMap.put("PURPLE_RED" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("BROWN_SUBS" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.5, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("BROWN_SUBS" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 10.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("BROWN_SUBS" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.5, 10.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("BROWN_SUBS" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 10.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("BROWN_SUBS" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 5.0, 10.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("BROWN_SUBS" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.5, 12.0, starFactory);
			}
		});
		// ice mini planetoid
		ruleMap.put("BROWN_SUBS" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("BROWN_SUBS" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("BROWN_SUBS" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("BROWN_SUBS" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.5, 12.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("BROWN_SUBS" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.5, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("BROWN_SUBS" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 14.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("BROWN_SUBS" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("BROWN_SUBS" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 14.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("BROWN_SUBS" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.5, 14.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		//cryogenic super terran planetoid
		ruleMap.put("BROWN_SUBS" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 16.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("BROWN_SUBS" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 16.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("BROWN_SUBS" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("BROWN_SUBS" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 16.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("BROWN_SUBS" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 16.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("BROWN_SUBS" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant 
		ruleMap.put("BROWN_SUBS" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// gas poor gas giant or super terran planetoid
		ruleMap.put("BROWN_SUBS" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 16.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("BROWN_SUBS" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.3, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("BROWN_SUBS" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 18.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("BROWN_SUBS" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 17.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("BROWN_SUBS" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 17.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("BROWN_SUBS" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 19.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("BROWN_SUBS" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 0.5, 19.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("BROWN_SUBS" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 0.5, 20.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("BROWN_SUBS" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 18.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("BROWN_SUBS" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.8, 18.0, starFactory);
			}
		});
		// gas poor super gas giant
		ruleMap.put("BROWN_SUBS" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 19.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("BROWN_SUBS" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 20.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("BROWN_SUBS" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.8, 19.0, starFactory);
			}
		});

		ruleMap.put("BROWN_SUBS" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic dwarf planetoid
		ruleMap.put("DRKBRN_SDW" + "0" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 10.0, starFactory);
			}
		});
		// ice dwarf planetoid
		ruleMap.put("DRKBRN_SDW" + "0" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.5, 10.0, starFactory);
			}
		});
		// rock dwarf planetoid
		ruleMap.put("DRKBRN_SDW" + "0" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 10.0, starFactory);
			}
		});
		// cythian dwarf planetoid
		ruleMap.put("DRKBRN_SDW" + "0" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 10.0, starFactory);
			}
		});
		// molten dwarf planetoid
		ruleMap.put("DRKBRN_SDW" + "0" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.0, 10.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "0" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be");
			}
		});
		// cryogenic mini planetoid
		ruleMap.put("DRKBRN_SDW" + "1" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 12.0, starFactory);
			}
		});
		// ice mimni planetoid
		ruleMap.put("DRKBRN_SDW" + "1" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 3.5, 12.0, starFactory);
			}
		});
		// rock mini planetoid
		ruleMap.put("DRKBRN_SDW" + "1" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 12.0, starFactory);
			}
		});
		// cythian mini planetoid
		ruleMap.put("DRKBRN_SDW" + "1" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 12.0, starFactory);
			}
		});
		// molten mini planetoid
		ruleMap.put("DRKBRN_SDW" + "1" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 12.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "1" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic terran planetoid
		ruleMap.put("DRKBRN_SDW" + "2" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 14.0, starFactory);
			}
		});
		// ice terran planetoid
		ruleMap.put("DRKBRN_SDW" + "2" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 14.0, starFactory);
			}
		});
		// rock terran planetoid
		ruleMap.put("DRKBRN_SDW" + "2" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 14.0, starFactory);
			}
		});
		// cythian terran planetoid
		ruleMap.put("DRKBRN_SDW" + "2" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 14.0, starFactory);
			}
		});
		// molten terran planetoid
		ruleMap.put("DRKBRN_SDW" + "2" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 4.0, 14.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "2" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "3" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.5, 16.0, starFactory);
			}
		});
		// ice super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "3" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// rock super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "3" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 16.0, starFactory);
			}
		});
		// cythian super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "3" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.25, 16.0, starFactory);
			}
		});
		// molten super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "3" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 16.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "3" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic mini gas giant
		ruleMap.put("DRKBRN_SDW" + "4" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 16.0, starFactory);
			}
		});
		// ice mini gas giant
		ruleMap.put("DRKBRN_SDW" + "4" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.75, 16.0, starFactory);
			}
		});
		// gas poor mini gas giant or super terran planetoid
		ruleMap.put("DRKBRN_SDW" + "4" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 16.0, starFactory);
			}
		});
		// hot mini gas giant or mini gas giant
		ruleMap.put("DRKBRN_SDW" + "4" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.2, 18.0, starFactory);
			}
		});
		// ultra hot mini gas giant
		ruleMap.put("DRKBRN_SDW" + "4" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 19.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "4" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic gas giant
		ruleMap.put("DRKBRN_SDW" + "5" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 2.0, 18.0, starFactory);
			}
		});
		// ice gas giant
		ruleMap.put("DRKBRN_SDW" + "5" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.4, 18.0, starFactory);
			}
		});
		// gas poor gas giant or ultra terran planetoid
		ruleMap.put("DRKBRN_SDW" + "5" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 20.0, starFactory);
			}
		});
		// hot gas giant or gas giant
		ruleMap.put("DRKBRN_SDW" + "5" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 0.25, 20.0, starFactory);
			}
		});
		// ultra hot gas giant
		ruleMap.put("DRKBRN_SDW" + "5" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 0.25, 21.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "5" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});
		// cryogenic super gas giant
		ruleMap.put("DRKBRN_SDW" + "6" + "0", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.5, 19.0, starFactory);
			}
		});
		// ice super gas giant
		ruleMap.put("DRKBRN_SDW" + "6" + "1", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.3, 19.0, starFactory);
			}
		});
		// rock super gas giant
		ruleMap.put("DRKBRN_SDW" + "6" + "2", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 20.0, starFactory);
			}
		});
		// hot super gas giant or super gas giant
		ruleMap.put("DRKBRN_SDW" + "6" + "3", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 0.25, 21.0, starFactory);
			}
		});
		// ultra hot super gas giant
		ruleMap.put("DRKBRN_SDW" + "6" + "4", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				return common(">", 1.0, 20.0, starFactory);
			}
		});

		ruleMap.put("DRKBRN_SDW" + "6" + "5", new AtmosphereResolutionIF() {
			public List<Atmosphere> resolve(StarFactory starFactory) {
				throw new RuntimeException("cannot be ...");
			}
		});

	}

	private static GenAtmosphere genAtmosphere = new GenAtmosphere();
	private static List<RadiusRange> planetsRadius = new ArrayList<RadiusRange>();
	static {
		planetsRadius.add(new RadiusRange(RadiusRangeConst.DWARF_PLANETOID,
				RadiusRange.s_DWARF_PLANETOID, 0)); // dwarf planetoid start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.MINI_PLANETOID,
				RadiusRange.s_MINI_PLANETOID, 1)); // mini planetoid start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.EARTH_PLANETOID,
				RadiusRange.s_EARTH_PLANETOID, 2)); // terran planetoid start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.SUPER_EARTH_PLANETOID,
				RadiusRange.s_SUPER_EARTH_PLANETOID, 3)); // super terran planetoid start
															// range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.MINI_GAS_GIANT_PLANETOID,
				RadiusRange.s_MINI_GAS_GIANT_PLANETOID, 4)); // mini gas giant planetoid
													// start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.GAS_GIANT_PLANETOID,
				RadiusRange.s_GAS_GIANT_PLANETOID, 5)); // gas giant planetoid start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.SUPER_GAS_GIANT_PLANETOID,
				RadiusRange.s_SUPER_GAS_GIANT_PLANETOID, 6)); // super gas giant planetoid
													// start range
		planetsRadius.add(new RadiusRange(RadiusRangeConst.THE_END, "end", 7));
	}

	private static List<TemperatureRange> temperaturesRange = new ArrayList<TemperatureRange>();
	static {
		temperaturesRange.add(new TemperatureRange(
				new Double(0.0), TemperatureRange.CRYOGENIC, 0)); // cryogenic start range
		temperaturesRange.add(new TemperatureRange(new Double(
				41.0), TemperatureRange.ICE, 1)); // ice start range
		temperaturesRange.add(new TemperatureRange(new Double(
				101.0), TemperatureRange.TEMPERATE, 2)); // rock, terran like range
		temperaturesRange.add(new TemperatureRange(new Double(
				701.0), TemperatureRange.HOT, 3)); // cythian, or hot gas
		temperaturesRange.add(new TemperatureRange(new Double(
				900.0), TemperatureRange.MOLTEN, 4)); // gaseous metals ...
		temperaturesRange.add(new TemperatureRange(
				Double.MAX_VALUE, "end", 5));
	}

	public List<Atmosphere> persistAtmosphere(Star star, Planetoid planetoid) {
		List<Atmosphere> atmospheres = null;

		StarFactory starFactory = StarFactory.accessByFullName(star
				.getStar_color());
		Double planetoidTemperature = planetoid.getTemperature();
		Double planetoidRadius = planetoid.getRadius();
		logger.info("PLANETOID RADIUS:"+planetoidRadius);
		Integer radiusKey = null;
		for (int idex = 0;; idex++) {
			if (planetoidRadius >= planetsRadius.get(idex).radius
					&& planetoidRadius < planetsRadius.get(idex + 1).radius) {
				radiusKey = planetsRadius.get(idex).key;
				logger.info("PLANET_SIZE:"+planetsRadius.get(idex).description);
				break;
			}
		}
		Integer temperatureKey = null;
		for (int idex = 0;; idex++) {
			if (planetoidTemperature >= temperaturesRange.get(idex).temperature
					&& planetoidTemperature < temperaturesRange.get(idex + 1).temperature) {
				temperatureKey = temperaturesRange.get(idex).key;
				logger.info("TEMPERATURE_TYPE:"+temperaturesRange.get(idex).description);
				break;
			}
		}

		String key = starFactory.name() + radiusKey
				+ temperatureKey;

		atmospheres = ruleMap.get(key).resolve(starFactory);
		PostProcessAtmosphere.processByRules(atmospheres, planetoid);
		List<Atmosphere> savedAtmospheres = new ArrayList<Atmosphere>();
		AtmosphereDao atmosphereDao = new AtmosphereDao();
		for(Atmosphere atmosphere : atmospheres){
			atmosphere.setPlanetoidId(planetoid.getPlanetoidId());
			savedAtmospheres.add(atmosphereDao.addAtmosphere(atmosphere));
		}
		return savedAtmospheres;
	}
	
	public static String temperatureType(Double temperature) {
		String temperatureType = "";
		for (int idex = 0;; idex++) {
			Double previousTemperature = temperaturesRange.get(idex).temperature;
			Double currrentTemperature = temperaturesRange.get(idex + 1).temperature;
			if (temperature >= previousTemperature
					&& temperature < currrentTemperature) {
				temperatureType = temperaturesRange.get(idex).description;
				break;
			}
		}
		return temperatureType;
	}

	public static String sizeType(Double radius){
		String sizeType = "";
		for (int idex = 0;; idex++) {
			
		    Double previousRadius = planetsRadius.get(idex).radius;
			Double currentRadius = planetsRadius.get(idex+1).radius;
			if(radius >= previousRadius && radius < currentRadius){
				sizeType = planetsRadius.get(idex).description;
				break;
			}
		}		
		return sizeType;
	}
}
