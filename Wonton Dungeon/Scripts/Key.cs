using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Key : Interact
{
    private int BatteryCount;

    // Increases Key Count and Removes from world
    public override void OnInteract()
    {
        gameObject.SetActive(false);
        inventory.setKeyCount(inventory.getKeyCount() + 1);
    }
}
