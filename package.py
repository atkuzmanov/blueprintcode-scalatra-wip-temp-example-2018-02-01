def package(self):
    specfile = self.spec
    specfile.set_build_arch("x86_64")
    location_to_my_jar = self.root +"/target/scala-2.11/" + self.name + ".jar"
    location_to_init_d_script = self.root +"/initd_script.sh"
    location_to_whitelist_test = self.root + "/conf/whitelist/test.txt"
    location_to_whitelist_live = self.root + "/conf/whitelist/live.txt"

    # Copy the required files in src/ into the sources dir
    self.run(["cp", "-R", location_to_my_jar, self.sources_dir])
    self.run(["cp", "-R", location_to_init_d_script, self.sources_dir])
    self.run(["cp", "-R", location_to_whitelist_test, self.sources_dir])
    self.run(["cp", "-R", location_to_whitelist_live, self.sources_dir])

    # Add them as sources in the specfile
    source = specfile.add_source(self.name + ".jar")
    init_d_script = specfile.add_source("initd_script.sh")
    whitelist_source_test = specfile.add_source("conf/whitelist/test.txt")
    whitelist_source_live = specfile.add_source("conf/whitelist/live.txt")

    # Add the required install steps
    specfile.add_install_steps([
        ["mkdir", "-p", "%{buildroot}/usr/lib/" + self.name],
        ["cp", "-R", source, "%{buildroot}/usr/lib/" + self.name],
        ["mkdir", "-p", "%{buildroot}%{_initddir}"],
        ["cp", "-R", init_d_script, "%{buildroot}%{_initddir}/" + self.name],
        ["mkdir", "-p", "%{buildroot}/usr/lib/"+ self.name + "/conf/whitelist/"],
        ["cp", "-R", whitelist_source_test, "%{buildroot}/usr/lib/" + self.name + "/conf/whitelist/test.txt"],
        ["cp", "-R", whitelist_source_live, "%{buildroot}/usr/lib/" + self.name + "/conf/whitelist/live.txt"]
    ])

    # Add the required files permissions
    specfile.add_files(["/usr/lib/" + self.name])
    self.spec.add_files(
        ["%{_initddir}/" + self.name],
        file_permissions=755,
        dir_permissions=755
    )
