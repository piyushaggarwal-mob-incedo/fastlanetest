//
//  SFAutoPlayViewModuleViewParser.swift
//  AppCMS
//
//  Created by Anirudh Vyas on 01/09/17.
//  Copyright © 2017 Viewlift. All rights reserved.
//

import Foundation

class SFAutoPlayViewModuleViewParser: NSObject {
    
    func parseLayoutJson(viewModuleDictionary: Dictionary<String, AnyObject>) -> SFAutoPlayViewModuleViewObject {
        let associatedViewObject = SFAutoPlayViewModuleViewObject()
        
        associatedViewObject.moduleID = viewModuleDictionary["id"] as? String
        associatedViewObject.moduleType = viewModuleDictionary["view"] as? String
        associatedViewObject.moduleTitle = viewModuleDictionary["title"] as? String
        
        var componentArray : Array<Dictionary<String, AnyObject>>?
        let layoutObjectParser = LayoutObjectParser()
        
        
        if DEBUGMODE {
            var filePath:String
            filePath = (Bundle.main.resourcePath?.appending("/AutoPlayLandscape_AppleTV.json"))!
            if FileManager.default.fileExists(atPath: filePath){
                let jsonData:Data = FileManager.default.contents(atPath: filePath)!
                let responseJson: Dictionary<String, Any> = try! JSONSerialization.jsonObject(with:jsonData) as! Dictionary<String, AnyObject>
                let layoutDict = responseJson["layout"] as? Dictionary<String, Any>
                componentArray = responseJson["components"] as? Array<Dictionary<String, AnyObject>>
                if componentArray != nil {
                    associatedViewObject.components = componentConfigArray(componentsArray: componentArray!)
                }
                associatedViewObject.layoutObjectDict = layoutObjectParser.parseLayoutJson(layoutDictionary: layoutDict!)
            }
        } else {
            componentArray = viewModuleDictionary["components"] as? Array<Dictionary<String, AnyObject>>
            
            if componentArray != nil {
                associatedViewObject.components = componentConfigArray(componentsArray: componentArray!)
            }
            
            associatedViewObject.layoutObjectDict = layoutObjectParser.parseLayoutJson(layoutDictionary: viewModuleDictionary["layout"] as! Dictionary<String, Any>)
        }
        return associatedViewObject
    }
    
    func componentConfigArray(componentsArray:Array<Dictionary<String, AnyObject>>) -> Array<AnyObject> {
        
        var componentArray:Array<AnyObject> = []
        
        for moduleDictionary: Dictionary<String, AnyObject> in componentsArray {
            
            let typeOfModule: String? = moduleDictionary["type"] as? String
            
            if typeOfModule == "button" {
                let buttonParser = SFButtonParser()
                let buttonObject = buttonParser.parseButtonJson(buttonDictionary: moduleDictionary as Dictionary<String, AnyObject>)
                if buttonObject.layoutObjectDict.isEmpty == false {
                    componentArray.append(buttonObject)
                }
            }
            else if typeOfModule == "image" || typeOfModule == "imageView" {
                let imageParser = SFImageParser()
                let imageObject = imageParser.parseImageJson(imageDictionary: moduleDictionary as Dictionary<String, AnyObject>)
                if imageObject.layoutObjectDict.isEmpty == false {
                    componentArray.append(imageObject)
                }
            }
            else if typeOfModule == "label" {
                let labelParser = SFLabelParser()
                let labelObject = labelParser.parseLabelJson(labelDictionary: moduleDictionary as Dictionary<String, AnyObject>)
                if labelObject.layoutObjectDict.isEmpty == false {
                    componentArray.append(labelObject)
                }
            }
            else if typeOfModule == "rotatingLoaderView" {
                let loaderViewParser = SFTimerLoaderViewParser()
                let loaderViewObject = loaderViewParser.parserLayoutJson(viewModuleDictionary: moduleDictionary as Dictionary<String, AnyObject>)
                if loaderViewObject.layoutObjectDict.isEmpty == false {
                    componentArray.append(loaderViewObject)
                }
            }
        }
        
        return componentArray
    }
}