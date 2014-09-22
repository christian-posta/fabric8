/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.jolokia.facade.facades;

import io.fabric8.api.HasId;
import io.fabric8.api.Profile;
import io.fabric8.api.Profiles;
import io.fabric8.jolokia.facade.utils.Helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;

/**
 * [TODO] Review ProfileFacade for profile consistentcy
 */
public class ProfileFacade implements Profile, HasId {

    J4pClient j4p;
    String id;
    String versionId;

    public ProfileFacade(J4pClient j4p, String versionId, String id) {
        this.j4p = j4p;
        this.versionId = versionId;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getVersion() {
        return versionId;
    }

    public Profile createProfile(String versionId, String s) {
        Map<String, Object> profile = Helpers.exec(j4p, "createProfile(java.lang.String, java.lang.String)", id, s);
        if (profile == null) {
            return null;
        }
        return new ProfileFacade(j4p, id, s);
    }

    public void deleteVersion(String versionId) {
        Helpers.exec(j4p, "deleteVersion(java.lang.String)", versionId);
    }

    @Override
    public Map<String, String> getAttributes() {
        return getFieldValue("attributes");
    }

    @Override
    public List<String> getParentIds() {
        List<String> profiles = getFieldValue("parents");
        return profiles != null ? profiles : Collections.<String>emptyList();
    }

    @Override
    public List<String> getLibraries() {
        return getFieldValue("libraries");
    }

    @Override
    public List<String> getEndorsedLibraries() {
        return getFieldValue("endorsedLibraries");
    }

    @Override
    public List<String> getExtensionLibraries() {
        return getFieldValue("extensionLibraries");
    }

    @Override
    public List<String> getBundles() {
        return getFieldValue("bundles");
    }

    @Override
    public List<String> getFabs() {
        return getFieldValue("fabs");
    }

    @Override
    public List<String> getFeatures() {
        return getFieldValue("features");
    }

    @Override
    public List<String> getRepositories() {
        return getFieldValue("repositories");
    }

    @Override
    public List<String> getOverrides() {
        return getFieldValue("overrides");
    }

    @Override
    public List<String> getOptionals() {
        return getFieldValue("optionals");
    }

    @Override
    public List<String> getTags() {
        return getFieldValue("tags");
    }
    
    @Override
    public Set<String> getConfigurationFileNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, byte[]> getFileConfigurations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getFileConfiguration(String fileName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Map<String, String>> getConfigurations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOverlay() {
        return (Boolean)getFieldValue("overlay");
    }

    @Override
    public boolean isAbstract() {
        return (Boolean)getFieldValue("abstract");
    }

    @Override
    public boolean isLocked() {
        return (Boolean)getFieldValue("locked");
    }

    @Override
    public boolean isHidden() {
        return (Boolean)getFieldValue("hidden");
    }

    @Override
    public String getProfileHash() {
        return getFieldValue("lastModified");
    }

    @Override
    public int compareTo(Profile profile) {
        throw new UnsupportedOperationException();
    }

	@Override
    public Map<String, String> getConfiguration(String pid) {
        Map<String, Map<String, String>> configurations = getConfigurations();
        if (configurations != null) {
            return configurations.get(pid);
        }
        return null;
    }

    public Profile setOptionals(List<String> strings) {
        Helpers.exec(j4p, "setProfileOptionals(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setParents(Profile[] profiles) {
        List<String> parentIds = new ArrayList<String>();
        for (Profile profile : profiles) {
            parentIds.add(profile.getId());
        }
        Helpers.exec(j4p, "changeProfileParents(java.lang.String, java.lang.String, java.util.List)", versionId, id, parentIds);
        return null;
    }

	public Profile setAttribute(String key, String value) {
        throw new UnsupportedOperationException();
	}

    public Profile setConfigurations(Map<String, Map<String, String>> stringMapMap) {
        throw new UnsupportedOperationException();
    }

    public Profile setFileConfigurations(Map<String, byte[]> stringMap) {
        throw new UnsupportedOperationException();
    }

    public void deleteProfile(String versionId, String profileId) {
        Helpers.exec(j4p, "deleteProfile(java.lang.String, java.lang.String)", versionId, profileId);
    }

    public Profile setBundles(List<String> strings) {
        Helpers.exec(j4p, "setProfileBundles(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setFabs(List<String> strings) {
        Helpers.exec(j4p, "setProfileFabs(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setFeatures(List<String> strings) {
        Helpers.exec(j4p, "setProfileFeatures(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setRepositories(List<String> strings) {
        Helpers.exec(j4p, "setProfileRepositories(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setOverrides(List<String> strings) {
        Helpers.exec(j4p, "setProfileOverrides(java.lang.String, java.lang.String, java.util.List)", versionId, id, strings);
        return null;
    }

    public Profile setConfiguration(String pid, Map<String, String> configuration) {
        Map<String, Map<String, String>> configurations = getConfigurations();
        if (configurations != null) {
            configurations.put(pid, configuration);
            setConfigurations(configurations);
        }
        return null;
    }

    public Profile setConfigurationFile(String fileName, byte[] data) {
        throw new UnsupportedOperationException();
    }

	@SuppressWarnings("unchecked")
	private static <T extends Object> T getFieldValue(J4pClient j4p, String operation, String versionId, String id, String field) {
        T rc = null;
        try {
            J4pExecRequest request = Helpers.createExecRequest(operation, versionId, id, Helpers.toList(field));
            J4pExecResponse response = j4p.execute(request);
            Map<String, Object> value = response.getValue();
            rc = (T)value.get(field);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Failed to get container field", e);
        } catch (J4pException e) {
            throw new RuntimeException("Failed to get container field", e);
        }
        return rc;
    }

    private <T extends Object> T getFieldValue(String field) {
        return getFieldValue(j4p, "getProfile(java.lang.String, java.lang.String, java.util.List)", versionId, id, field);
    }

    @Override
    public String getIconURL() {
        return getFieldValue("iconURL");
    }

    @Override
    public String getIconRelativePath() {
  return getFieldValue("iconRelativePath");
    }

    @Override
    public String getSummaryMarkdown() {
        throw new UnsupportedOperationException();
    }
}
