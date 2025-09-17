/*
 *    Copyright 2015-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package gov.utleg.dao;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

//import gov.utleg.bigboard.dao.olag.OrganizationDao;
//import gov.utleg.bigboard.model.olag.Organization;


@MybatisTest
class OrgDaoTest {

  @Autowired
//  private OrganizationDao orgDao;

  @Test
  void findByStateTest() {
	  
//	  Organization org = orgDao.getAllOrganization().get(0);
//	  assertThat(org.getOrgId() == 1);
//	  assertThat(org.getOrgName()).isEqualTo("SENATE");
  }

}
