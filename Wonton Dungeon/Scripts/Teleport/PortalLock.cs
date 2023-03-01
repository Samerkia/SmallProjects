using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PortalLock : Interact
{
    public override void OnInteract()
    {
        if (inventory.getKeyCount() >= 3)
        {
            inventory.setKeyCount(0);
            gameObject.SetActive(false);
        }
    }
}
