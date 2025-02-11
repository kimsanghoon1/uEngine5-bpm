package org.uengine.modeling.resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.nio.file.StandardCopyOption.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jangjinyoung on 15. 7. 12..
 */
@Component
public class LocalFileStorage extends AbstractStorage{

    @Override
    public void delete(IResource fileResource) throws IOException {
        File file = getFile(fileResource);

        if(file.isDirectory()){
            FileUtils.deleteDirectory(file);
        }else{
            file.delete();

        }
    }

    @Override
    public void rename(IResource fileResource, String newName) {
        getFile(fileResource).renameTo(new File(getTenantBasePath() + newName));
    }

    @Override
    public void copy(IResource src, String desPath) throws IOException {
        File destinationFile = new File(getTenantBasePath() + desPath);
        File sourceFile = getFile(src);

        if(sourceFile.isDirectory()){
            FileUtils.copyDirectory(getFile(src), destinationFile);
        }else{
            destinationFile.getParentFile().mkdirs();

            if(isDoNotOverwrite())
                Files.copy(getFile(src).toPath(), destinationFile.toPath());
            else
                Files.copy(getFile(src).toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    @Override
    public List<IResource> listFiles(IContainer containerResource) throws Exception {
        List<IResource> resourceList = new ArrayList<IResource>();

        File directory = getFile(containerResource);

        String tenantBasePath = getTenantBasePath();

        File tenantBase = new File(tenantBasePath);
        if(!tenantBase.exists()){
            tenantBase.mkdirs();
        }

        String abstractTenantBasePath = new File(tenantBasePath).getAbsolutePath();

        if(directory!=null && directory.exists())
        for(File file : directory.listFiles()){

            if(file.getName().startsWith(".")) continue;

            String relativePath = file.getAbsolutePath().replace("\\", "/");

            relativePath = relativePath.substring(abstractTenantBasePath.length() + 1);

            if(file.isDirectory()){
                ContainerResource subContainerResource = (ContainerResource) containerResource.getClass().newInstance();

                subContainerResource.setPath(relativePath);

//                if(false)
//                    subContainerResource.setChildren(listFiles(subContainerResource));

                resourceList.add(subContainerResource);
            }else{
                resourceList.add(DefaultResource.createResource(relativePath));
            }
        }

        return resourceList;
    }

    @Override
    public void createFolder(IContainer containerResource) throws Exception {
        File directory = getFile(containerResource);

        directory.mkdirs();
    }

    @Override
    public boolean exists(IResource resource) throws Exception {
        return getFile(resource).exists();
    }

    @Override
    public Object getObject(IResource resource) throws Exception {
        return Serializer.deserialize(getInputStream(resource));
    }

    @Override
    public void save(IResource resource, Object object) throws Exception {

        File directory = getFile(resource).getParentFile();

        if(!directory.exists())
            directory.mkdirs();


        Serializer.serialize(object, getOutputStream(resource));

    }

    @Override
    public InputStream getInputStream(IResource resource) throws Exception {
        return new FileInputStream(getFile(resource));
    }

    @Override
    public OutputStream getOutputStream(IResource resource) throws Exception {
        File file = getFile(resource);
        File directory = file.getParentFile();

        if(!directory.exists())
            directory.mkdirs();

        return new FileOutputStream(file);
    }

    @Override
    public boolean isContainer(IResource resource) throws Exception {
        return getFile(resource).isDirectory();
    }

    private File getFile(IResource fileResource) {
        String tenantBasePath = getTenantBasePath();

        return new File(tenantBasePath
                + fileResource.getPath());
    }


    @Override
    public void move(IResource src, IContainer container) throws IOException {
        Path source = getFile(src).toPath();
        Path target = new File(getTenantBasePath() + container.getPath() + "/" + src.getName()).toPath();
        Files.move(source, target, REPLACE_EXISTING);
    }
}
